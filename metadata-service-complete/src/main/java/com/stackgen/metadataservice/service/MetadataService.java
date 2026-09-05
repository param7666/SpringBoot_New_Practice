package com.stackgen.metadataservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackgen.metadataservice.dto.*;
import com.stackgen.metadataservice.entity.*;
import com.stackgen.metadataservice.repository.*;
import com.stackgen.metadataservice.util.ExcelParser;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

@Service
public class MetadataService {
 private final ObjectMapper mapper;
 private final EntityMetadataRepository entityRepo; private final EntityFieldRepository fieldRepo; private final EntityRelationshipRepository relRepo;
 private final ApiEndpointRepository endpointRepo; private final EndpointPathVariableRepository variableRepo;
 private final PageRepository pageRepo; private final PageComponentRepository componentRepo; private final ComponentEventRepository eventRepo; private final WireframeUploadRepository wireframeRepo;
 public MetadataService(ObjectMapper mapper,EntityMetadataRepository entityRepo,EntityFieldRepository fieldRepo,EntityRelationshipRepository relRepo,ApiEndpointRepository endpointRepo,EndpointPathVariableRepository variableRepo,PageRepository pageRepo,PageComponentRepository componentRepo,ComponentEventRepository eventRepo,WireframeUploadRepository wireframeRepo){this.mapper=mapper;this.entityRepo=entityRepo;this.fieldRepo=fieldRepo;this.relRepo=relRepo;this.endpointRepo=endpointRepo;this.variableRepo=variableRepo;this.pageRepo=pageRepo;this.componentRepo=componentRepo;this.eventRepo=eventRepo;this.wireframeRepo=wireframeRepo;}

 public ReadResponse read(MultipartFile file)throws IOException{var p=ExcelParser.parse(file);return new ReadResponse(p.headers(),p.rows(),p.rows().size());}

 public ValidationResponse validate(MultipartFile file)throws IOException{
  var p=ExcelParser.parse(file); List<ValidationIssue> issues=new ArrayList<>();
  Set<String> tables=new HashSet<>(); Map<String,Set<String>> columns=new HashMap<>(); Map<String,Set<String>> pk=new HashMap<>();
  int row=1;
  for(Map<String,String> r:p.rows()){
   String service=r.get("service"),table=r.get("table"),column=r.get("column"),type=r.get("data type"),key=r.get("key"),ref=r.get("references");
   if(!service.toLowerCase().contains("metadata service - backend")) {row++;continue;}
   if(table.isBlank()) issues.add(new ValidationIssue("ERROR",p.sheetName(),table,column,"Table is required"));
   if(column.isBlank()) issues.add(new ValidationIssue("ERROR",p.sheetName(),table,column,"Column is required"));
   if(!table.isBlank()) {tables.add(table);columns.computeIfAbsent(table.toLowerCase(),k->new HashSet<>()).add(column.toLowerCase());}
   if("PK".equalsIgnoreCase(key)) pk.computeIfAbsent(table.toLowerCase(),k->new HashSet<>()).add(column.toLowerCase());
   if(type.isBlank()) issues.add(new ValidationIssue("ERROR",p.sheetName(),table,column,"Data Type is required"));
   if(key.equalsIgnoreCase("FK")&&ref.isBlank()) issues.add(new ValidationIssue("ERROR",p.sheetName(),table,column,"FK must have a References value such as users.id"));
   row++;
  }
  for(Map<String,String> r:p.rows()){
   String service=r.get("service"); if(!service.toLowerCase().contains("metadata service - backend")) continue;
   String ref=r.get("references"); if(ref.isBlank()) continue; String[] x=ref.split("\\.",2); if(x.length!=2) {issues.add(new ValidationIssue("ERROR",p.sheetName(),r.get("table"),r.get("column"),"Reference must be table.column: "+ref));continue;}
   if(!tables.stream().anyMatch(t->t.equalsIgnoreCase(x[0]))) issues.add(new ValidationIssue("ERROR",p.sheetName(),r.get("table"),r.get("column"),"Referenced table does not exist: "+x[0]));
   else if(!columns.getOrDefault(x[0].toLowerCase(),Set.of()).contains(x[1].toLowerCase())) issues.add(new ValidationIssue("ERROR",p.sheetName(),r.get("table"),r.get("column"),"Referenced column does not exist: "+ref));
  }
  return new ValidationResponse(issues.stream().noneMatch(i->i.severity().equals("ERROR")),issues);
 }

 @Transactional
 public ExtractResponse extract(UUID projectId, MultipartFile file, String frontendJson)throws IOException{
  if(projectId==null) throw new IllegalArgumentException("projectId is required");
  ValidationResponse vr=validate(file); if(!vr.valid()) throw new IllegalArgumentException("Validation failed: "+vr.issues().stream().map(ValidationIssue::message).collect(Collectors.joining("; ")));
  var p=ExcelParser.parse(file);
  clearProject(projectId);
  Map<String,EntityMetadata> entities=new LinkedHashMap<>(); int fieldCount=0;
  for(Map<String,String> r:p.rows()){
   if(!r.get("service").toLowerCase().contains("metadata service - backend")) continue;
   String table=r.get("table"); if(table.isBlank()) continue;
   EntityMetadata e=entities.get(table.toLowerCase());
   if(e==null){e=new EntityMetadata();e.setProjectId(projectId);e.setTableName(table);e.setName(toClassName(table));e=entityRepo.save(e);entities.put(table.toLowerCase(),e);}
   EntityField f=new EntityField(); f.setEntityId(e.getId()); f.setName(r.get("column")); f.setDataType(r.get("data type"));
   String c=r.get("constraints").toUpperCase(); String k=r.get("key").toUpperCase(); f.setPrimaryKey(k.contains("PK")||c.contains("PRIMARY KEY")); f.setNullable(!c.contains("NOT NULL")); f.setUnique(c.contains("UNIQUE")); f.setDefaultValue(extractDefault(c)); f.setValidations(validationJson(c)); f.setDisplayOrder(fieldCountFor(entities.get(table.toLowerCase()))); fieldRepo.save(f); fieldCount++;
  }
  int relCount=0;
  List<Map<String,String>> fkRows=p.rows().stream()
    .filter(r->r.get("service").toLowerCase().contains("metadata service - backend"))
    .filter(r->!r.get("references").isBlank()).toList();
  for(Map<String,String> r:fkRows){
   String ref=r.get("references"); String[] x=ref.split("\\.",2); if(x.length!=2) continue;
   EntityMetadata source=entities.get(r.get("table").toLowerCase()), target=entities.get(x[0].toLowerCase()); if(source==null||target==null) continue;
   EntityRelationship rel=new EntityRelationship(); rel.setProjectId(projectId);rel.setSourceEntityId(source.getId());rel.setTargetEntityId(target.getId());rel.setFieldName(r.get("column"));rel.setJoinColumn(r.get("column"));rel.setOwningSide(true);
   String constraints=r.get("constraints").toUpperCase(); String type=constraints.contains("UNIQUE")?"ONE_TO_ONE":"MANY_TO_ONE";
   rel.setRelationshipType(type); relRepo.save(rel); relCount++;
  }
  // If the dictionary explicitly names a junction/mapping table, also expose the logical MANY_TO_MANY pair.
  Map<String,List<Map<String,String>>> byTable=fkRows.stream().collect(Collectors.groupingBy(r->r.get("table").toLowerCase(),LinkedHashMap::new,Collectors.toList()));
  for(var entry:byTable.entrySet()){
   String joinTable=entry.getKey(); List<Map<String,String>> fks=entry.getValue();
   if(fks.size()!=2 || !joinTable.matches(".*(mapping|junction|join|association|link).*")) continue;
   Map<String,String> a=fks.get(0), b=fks.get(1); String[] ar=a.get("references").split("\\.",2), br=b.get("references").split("\\.",2);
   EntityMetadata ea=entities.get(ar[0].toLowerCase()), eb=entities.get(br[0].toLowerCase()); if(ea==null||eb==null||ea.getId().equals(eb.getId())) continue;
   EntityRelationship ab=new EntityRelationship(); ab.setProjectId(projectId);ab.setSourceEntityId(ea.getId());ab.setTargetEntityId(eb.getId());ab.setRelationshipType("MANY_TO_MANY");ab.setFieldName(joinTable);ab.setJoinColumn(joinTable);ab.setOwningSide(true);relRepo.save(ab);relCount++;
   EntityRelationship ba=new EntityRelationship(); ba.setProjectId(projectId);ba.setSourceEntityId(eb.getId());ba.setTargetEntityId(ea.getId());ba.setRelationshipType("MANY_TO_MANY");ba.setFieldName(joinTable);ba.setJoinColumn(joinTable);ba.setMappedBy(joinTable);ba.setOwningSide(false);relRepo.save(ba);relCount++;
  }
  generateCrudEndpoints(projectId,entities);
  if(frontendJson!=null&&!frontendJson.isBlank()) parseFrontend(projectId,frontendJson,entities);
  return new ExtractResponse(projectId,entities.size(),fieldCount,relCount,endpointRepo.findByProjectId(projectId).size(),"Metadata extracted and relationships resolved successfully");
 }

 private int fieldCountFor(EntityMetadata e){return fieldRepo.findByEntityIdOrderByDisplayOrder(e.getId()).size();}
 private String extractDefault(String c){int i=c.indexOf("DEFAULT "); if(i<0)return null; String v=c.substring(i+8).trim(); int comma=v.indexOf(','); return comma>0?v.substring(0,comma).trim():v;}
 private String validationJson(String c){List<String> x=new ArrayList<>(); if(c.contains("NOT NULL"))x.add("@NotNull"); if(c.contains("UNIQUE"))x.add("@Unique"); Pattern p=Pattern.compile("SIZE\\(MAX\\s*=\\s*(\\d+)\\)",Pattern.CASE_INSENSITIVE); Matcher m=p.matcher(c); if(m.find())x.add("@Size(max="+m.group(1)+")"); try{return mapper.writeValueAsString(x);}catch(Exception e){return "[]";}}
 private String toClassName(String s){String[] a=s.replaceAll("[^A-Za-z0-9_]","_").split("_");StringBuilder b=new StringBuilder();for(String x:a)if(!x.isBlank())b.append(Character.toUpperCase(x.charAt(0))).append(x.substring(1));return b.toString();}
 private void generateCrudEndpoints(UUID projectId,Map<String,EntityMetadata> entities){
  for(EntityMetadata e:entities.values()){String base="/api/"+e.getTableName(); createEndpoint(projectId,e,base,"GET","list"+e.getName(),null,e.getName()+"Response");createEndpoint(projectId,e,base,"POST","create"+e.getName(),e.getName()+"Request",e.getName()+"Response");createEndpoint(projectId,e,base+"/{id}","GET","get"+e.getName(),null,e.getName()+"Response");createEndpoint(projectId,e,base+"/{id}","PUT","update"+e.getName(),e.getName()+"Request",e.getName()+"Response");createEndpoint(projectId,e,base+"/{id}","PATCH","patch"+e.getName(),e.getName()+"Request",e.getName()+"Response");createEndpoint(projectId,e,base+"/{id}","DELETE","delete"+e.getName(),null,"void");}
 }
 private void createEndpoint(UUID projectId,EntityMetadata e,String path,String method,String op,String req,String resp){ApiEndpoint a=new ApiEndpoint();a.setProjectId(projectId);a.setEntityId(e.getId());a.setPath(path);a.setHttpMethod(method);a.setOperationName(op);a.setRequestDto(req);a.setResponseDto(resp);a.setRequiresAuth(true);a.setDescription("Generated CRUD endpoint for "+e.getTableName());endpointRepo.save(a);if(path.contains("/{id}")){EndpointPathVariable v=new EndpointPathVariable();v.setEndpointId(a.getId());v.setName("id");v.setDataType("UUID");variableRepo.save(v);}}

 private void parseFrontend(UUID projectId,String json,Map<String,EntityMetadata> entities)throws IOException{
  JsonNode root=mapper.readTree(json); JsonNode pages=root.has("pages")?root.get("pages"):root;
  if(!pages.isArray()) return;
  for(JsonNode p:pages){Page page=new Page();page.setProjectId(projectId);page.setName(text(p,"name","Page"));page.setRoute(text(p,"route","/"+page.getName().toLowerCase()));page.setPageType(text(p,"pageType","CUSTOM"));page.setShowInNav(p.path("showInNav").asBoolean(false));String entityName=text(p,"entity"," ").trim();if(!entityName.isBlank()&&entities.containsKey(entityName.toLowerCase()))page.setEntityId(entities.get(entityName.toLowerCase()).getId());page=pageRepo.save(page);
   JsonNode comps=p.has("components")?p.get("components"):p.get("pageComponents"); if(comps!=null&&comps.isArray()){int order=0;for(JsonNode c:comps){PageComponent pc=new PageComponent();pc.setPageId(page.getId());pc.setComponentType(text(c,"componentType",text(c,"type","input")));pc.setLabel(text(c,"label",null));pc.setBoundField(text(c,"boundField",text(c,"field",null)));pc.setConfig(c.has("config")?c.get("config").toString():"{}");pc.setDisplayOrder(order++);pc.setSource(text(c,"source","manual"));pc.setNeedsReview(c.path("needsReview").asBoolean(false));pc=componentRepo.save(pc);JsonNode events=c.get("events");if(events!=null&&events.isArray())for(JsonNode ev:events){ComponentEvent ce=new ComponentEvent();ce.setComponentId(pc.getId());ce.setTrigger(text(ev,"trigger","onClick"));ce.setAction(text(ev,"action","showToast"));ce.setTargetPageId(null);ce.setOnSuccessAction(ev.has("onSuccessAction")?ev.get("onSuccessAction").toString():null);ce.setOnErrorAction(ev.has("onErrorAction")?ev.get("onErrorAction").toString():null);eventRepo.save(ce);}}}
  }
 }
 private String text(JsonNode n,String field,String def){JsonNode x=n.get(field);return x==null||x.isNull()?def:x.asText();}
 private void clearProject(UUID projectId){List<EntityMetadata> es=entityRepo.findByProjectIdOrderByTableName(projectId);List<UUID> ids=es.stream().map(EntityMetadata::getId).toList();if(!ids.isEmpty())fieldRepo.deleteByEntityIdIn(ids);relRepo.deleteByProjectId(projectId);List<ApiEndpoint> aps=endpointRepo.findByProjectId(projectId);if(!aps.isEmpty())variableRepo.deleteByEndpointIdIn(aps.stream().map(ApiEndpoint::getId).toList());endpointRepo.deleteByProjectId(projectId);List<Page> ps=pageRepo.findByProjectIdOrderByName(projectId);List<UUID> pids=ps.stream().map(Page::getId).toList();if(!pids.isEmpty()){List<UUID> cids=new ArrayList<>();for(UUID p:pids)cids.addAll(componentRepo.findByPageIdOrderByDisplayOrder(p).stream().map(PageComponent::getId).toList());if(!cids.isEmpty())eventRepo.deleteByComponentIdIn(cids);componentRepo.deleteByPageIdIn(pids);}pageRepo.deleteByProjectId(projectId);entityRepo.deleteAll(es);}

 public MetadataResponse getMetadata(UUID projectId){List<EntityMetadataResponse> es=new ArrayList<>();Map<UUID,String> names=new HashMap<>();for(EntityMetadata e:entityRepo.findByProjectIdOrderByTableName(projectId)){names.put(e.getId(),e.getTableName());List<FieldMetadataResponse> fs=fieldRepo.findByEntityIdOrderByDisplayOrder(e.getId()).stream().map(f->new FieldMetadataResponse(f.getName(),f.getDataType(),f.isNullable(),f.isUnique(),f.isPrimaryKey(),f.getDefaultValue(),f.getValidations(),f.getDisplayOrder())).toList();es.add(new EntityMetadataResponse(e.getName(),e.getTableName(),fs));}List<RelationshipResponse> rs=relRepo.findByProjectIdOrderBySourceEntityId(projectId).stream().map(r->new RelationshipResponse(names.get(r.getSourceEntityId()),r.getFieldName(),names.get(r.getTargetEntityId()),r.getJoinColumn(),r.getRelationshipType(),r.getJoinColumn(),r.getMappedBy(),r.isOwningSide())).toList();List<ApiEndpointResponse> aps=endpointRepo.findByProjectId(projectId).stream().map(a->new ApiEndpointResponse(a.getPath(),a.getHttpMethod(),a.getOperationName(),a.getRequestDto(),a.getResponseDto(),a.isRequiresAuth(),a.getDescription())).toList();return new MetadataResponse(projectId,es,rs,aps,Map.of());}
}
