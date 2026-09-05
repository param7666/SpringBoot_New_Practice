package com.stackgen.metadataservice.controller;
import com.stackgen.metadataservice.dto.*; import com.stackgen.metadataservice.service.MetadataService; import org.springframework.http.MediaType; import org.springframework.web.bind.annotation.*; import org.springframework.web.multipart.MultipartFile; import java.io.IOException; import java.util.*;
@RestController @RequestMapping("/api/metadata")
public class MetadataController { private final MetadataService service; public MetadataController(MetadataService service){this.service=service;}
 @PostMapping(value="/read",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public ReadResponse read(@RequestPart("file") MultipartFile file)throws IOException{return service.read(file);}
 @PostMapping(value="/validate",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public ValidationResponse validate(@RequestPart("file") MultipartFile file)throws IOException{return service.validate(file);}
 @PostMapping(value="/extract",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public ExtractResponse extract(@RequestParam UUID projectId,@RequestParam(required=false) UUID userId,@RequestPart("file") MultipartFile file,@RequestPart(value="frontendJson",required=false) String frontendJson)throws IOException{return service.extract(projectId,file,frontendJson);}
 @GetMapping("/projects/{projectId}") public MetadataResponse get(@PathVariable UUID projectId){return service.getMetadata(projectId);}
}
