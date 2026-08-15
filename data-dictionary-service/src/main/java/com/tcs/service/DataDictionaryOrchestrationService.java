package com.tcs.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tcs.dto.DataDictionarySchema;
import com.tcs.dto.GenerationRequest;
import com.tcs.dto.GenerationResponse;
import com.tcs.dto.WireframeInfo;
import com.tcs.enums.RequestMode;

@Service
public class DataDictionaryOrchestrationService {

	private final DataDictionaryParserService parserService;
    private final WireframeService wireframeService;
    private final PromptTextBuilder promptTextBuilder;
    private final DjangoAiClient djangoAiClient;
    
    public DataDictionaryOrchestrationService(
            DataDictionaryParserService parserService,
            WireframeService wireframeService,
            PromptTextBuilder promptTextBuilder,
            DjangoAiClient djangoAiClient) {
        this.parserService = parserService;
        this.wireframeService = wireframeService;
        this.promptTextBuilder = promptTextBuilder;
        this.djangoAiClient = djangoAiClient;
    }
    
//    public GenerationResponse process(GenerationRequest request) {
//    	
//    	System.out.println("DataDictionaryOrchestrationService.process()");
//        String projectId = request.getProjectId() != null
//                ? request.getProjectId()
//                : UUID.randomUUID().toString();
// 
//        try {
//            RequestMode mode = promptTextBuilder.resolveMode(request);
// 
//            // Step 1: always parse the data dictionary
//            DataDictionarySchema schema = parserService.parse(request.getDataDictionaryFile());
// 
//            // Step 2: only describe the wireframe if this mode includes one
//            WireframeInfo wireframeInfo = null;
//            if (mode == RequestMode.DICTIONARY_WITH_WIREFRAME || mode == RequestMode.DICTIONARY_FULL) {
//                wireframeInfo = wireframeService.describeWireframe(request.getWireframeFile());
//            }
// 
//            // Step 3: build the combined text payload
//            String promptText = promptTextBuilder.buildText(
//                    mode, schema, wireframeInfo, request.getTechStack(), request.getExtraDetails());
//            
//            System.out.println("========== PROMPT TEXT SENT TO DJANGO AI SERVICE ==========");
//            System.out.println(promptText);
//            System.out.println("=============================================================");
//            
//            // Step 4: send to Django AI Service
//            String generatedCode = djangoAiClient.sendForGeneration(projectId, promptText);
// 
//            return new GenerationResponse(projectId, mode, generatedCode, "SUCCESS", "Generation completed");
// 
//        } catch (Exception e) {
//            return new GenerationResponse(projectId, null, null, "FAILED", e.getMessage());
//        }
//    }
    
    
    public GenerationResponse process(GenerationRequest request) {
        String projectId = request.getProjectId() != null
                ? request.getProjectId()
                : UUID.randomUUID().toString();
 
        try {
            RequestMode mode = promptTextBuilder.resolveMode(request);
 
            // Step 1: always parse the data dictionary (raw text dump, all sheets)
            String dictionaryRawText = parserService.parse(request.getDataDictionaryFile());
 
            // Step 2: only describe the wireframe if this mode includes one
            WireframeInfo wireframeInfo = null;
            if (mode == RequestMode.DICTIONARY_WITH_WIREFRAME || mode == RequestMode.DICTIONARY_FULL) {
                wireframeInfo = wireframeService.describeWireframe(request.getWireframeFile());
            }
 
            // Step 3: build the combined text payload
            String promptText = promptTextBuilder.buildText(
                    mode, dictionaryRawText, wireframeInfo, request.getTechStack(), request.getExtraDetails());
 
            // DEBUG: print the exact text payload that would be sent to Django.
            // This is the main thing to check while Django AI Service isn't built yet.
            System.out.println("========== PROMPT TEXT SENT TO DJANGO AI SERVICE ==========");
            System.out.println(promptText);
            System.out.println("=============================================================");
 
            // Step 4: send to Django AI Service.
            // Wrapped separately so a missing/unreachable Django service doesn't
            // hide the fact that parsing + text building worked fine.
            String generatedCode;
            try {
                generatedCode = djangoAiClient.sendForGeneration(projectId, promptText);
            } catch (Exception djangoCallFailed) {
                System.out.println("Django AI Service call failed (expected if it's not running yet): "
                        + djangoCallFailed.getMessage());
                // Return the built prompt text itself as "generatedCode" so you can
                // verify parsing/text-building end to end from the test HTML page.
                return new GenerationResponse(projectId, mode, promptText, "SUCCESS_NO_DJANGO",
                        "Django AI Service unreachable — showing built prompt text instead");
            }
 
            return new GenerationResponse(projectId, mode, generatedCode, "SUCCESS", "Generation completed");
 
        } catch (Exception e) {
            return new GenerationResponse(projectId, null, null, "FAILED", e.getMessage());
        }
    }
}
