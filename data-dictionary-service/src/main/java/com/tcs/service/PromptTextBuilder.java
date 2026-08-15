package com.tcs.service;

import org.springframework.stereotype.Service;

import com.tcs.dto.DataDictionarySchema;
import com.tcs.dto.FieldSchema;
import com.tcs.dto.GenerationRequest;
import com.tcs.dto.TableSchema;
import com.tcs.dto.WireframeInfo;
import com.tcs.enums.RequestMode;

@Service
public class PromptTextBuilder {

//	public RequestMode resolveMode(GenerationRequest req) {
//		boolean hasWireFrame=req.getWireframeFile()!=null && !req.getWireframeFile().isEmpty();
//		boolean hasExtraDetails=req.getExtraDetails()!=null && !req.getExtraDetails().isBlank();
//		
//		if(hasWireFrame && hasExtraDetails) return RequestMode.DICTIONARY_FULL;
//		if(hasWireFrame) return RequestMode.DICTIONARY_WITH_WIREFRAME;
//		return RequestMode.DICTIONARY_ONLY;
//		
//	}
//	
//	public String buildText(RequestMode mode,DataDictionarySchema schema, WireframeInfo info,String techStack,String extraDetails) {
//		
//		StringBuilder sb=new StringBuilder();
//		 sb.append("TECHNOLOGY STACK:\n").append(techStack).append("\n\n");
//		 sb.append("DATA DICTIONARY:\n");
//		 appendSchema(sb, schema);
//		 
//		 if (mode == RequestMode.DICTIONARY_WITH_WIREFRAME || mode == RequestMode.DICTIONARY_FULL) {
//	            sb.append("\nWIREFRAME:\n");
//	            sb.append(info != null ? info.getDescription() : "(none provided)").append("\n");
//	        }
//		 
//		 if (mode == RequestMode.DICTIONARY_FULL) {
//	            sb.append("\nEXTRA DETAILS:\n");
//	            sb.append(extraDetails).append("\n");
//	        }
//		 
//		 sb.append("\nINSTRUCTION:\n");
//	     sb.append(buildInstruction(mode));
//	     
//	     return sb.toString();
//		
//	}
//	
//	private void appendSchema(StringBuilder sb, DataDictionarySchema schema) {
//        for (TableSchema table : schema.getTables()) {
//            sb.append("Table: ").append(table.getTableName()).append("\n");
//            for (FieldSchema field : table.getFields()) {
//                sb.append("  - ").append(field.getName())
//                        .append(" (").append(field.getType()).append(")");
//                if (!field.getConstraints().isEmpty()) {
//                    sb.append(" [").append(String.join(", ", field.getConstraints())).append("]");
//                }
//                sb.append("\n");
//            }
//        }
//    }
// 
//    private String buildInstruction(RequestMode mode) {
//        return switch (mode) {
//            case DICTIONARY_ONLY -> "Generate backend code that implements this data schema using the given technology stack.";
//            case DICTIONARY_WITH_WIREFRAME -> "Generate code that implements this data schema and matches the given wireframe/UI flow, using the given technology stack.";
//            case DICTIONARY_FULL -> "Generate code that implements this data schema, matches the given wireframe/UI flow, and follows the additional requirements listed under EXTRA DETAILS, using the given technology stack.";
//        };
//    }
	
	public RequestMode resolveMode(GenerationRequest request) {
        boolean hasWireframe = request.getWireframeFile() != null && !request.getWireframeFile().isEmpty();
        boolean hasExtraDetails = request.getExtraDetails() != null && !request.getExtraDetails().isBlank();
 
        if (hasWireframe && hasExtraDetails) return RequestMode.DICTIONARY_FULL;
        if (hasWireframe) return RequestMode.DICTIONARY_WITH_WIREFRAME;
        return RequestMode.DICTIONARY_ONLY;
    }
 
    public String buildText(RequestMode mode, String dictionaryRawText, WireframeInfo wireframeInfo,
                             String techStack, String extraDetails) {
        StringBuilder sb = new StringBuilder();
 
        sb.append("TECHNOLOGY STACK:\n").append(techStack).append("\n\n");
 
        sb.append("DATA DICTIONARY (raw, may contain multiple sheets/sections):\n");
        sb.append(dictionaryRawText).append("\n");
 
        if (mode == RequestMode.DICTIONARY_WITH_WIREFRAME || mode == RequestMode.DICTIONARY_FULL) {
            sb.append("\nWIREFRAME:\n");
            sb.append(wireframeInfo != null ? wireframeInfo.getDescription() : "(none provided)").append("\n");
        }
 
        if (mode == RequestMode.DICTIONARY_FULL) {
            sb.append("\nEXTRA DETAILS:\n");
            sb.append(extraDetails).append("\n");
        }
 
        sb.append("\nINSTRUCTION:\n");
        sb.append(buildInstruction(mode));
 
        return sb.toString();
    }
 
    private String buildInstruction(RequestMode mode) {
        return switch (mode) {
            case DICTIONARY_ONLY -> "Interpret the data dictionary above (it may span multiple sheets/sections such as API specs, entities, relationships, roles). Generate backend code that implements the described schema and endpoints using the given technology stack.";
            case DICTIONARY_WITH_WIREFRAME -> "Interpret the data dictionary above (it may span multiple sheets/sections). Generate code that implements this schema and matches the given wireframe/UI flow, using the given technology stack.";
            case DICTIONARY_FULL -> "Interpret the data dictionary above (it may span multiple sheets/sections). Generate code that implements this schema, matches the given wireframe/UI flow, and follows the additional requirements listed under EXTRA DETAILS, using the given technology stack.";
        };
    }
    
}
