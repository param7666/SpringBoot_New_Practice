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
    
    public GenerationResponse process(GenerationRequest request) {
    	
    	System.out.println("DataDictionaryOrchestrationService.process()");
        String projectId = request.getProjectId() != null
                ? request.getProjectId()
                : UUID.randomUUID().toString();
 
        try {
            RequestMode mode = promptTextBuilder.resolveMode(request);
 
            // Step 1: always parse the data dictionary
            DataDictionarySchema schema = parserService.parse(request.getDataDictionaryFile());
 
            // Step 2: only describe the wireframe if this mode includes one
            WireframeInfo wireframeInfo = null;
            if (mode == RequestMode.DICTIONARY_WITH_WIREFRAME || mode == RequestMode.DICTIONARY_FULL) {
                wireframeInfo = wireframeService.describeWireframe(request.getWireframeFile());
            }
 
            // Step 3: build the combined text payload
            String promptText = promptTextBuilder.buildText(
                    mode, schema, wireframeInfo, request.getTechStack(), request.getExtraDetails());
            
            System.out.println("========== PROMPT TEXT SENT TO DJANGO AI SERVICE ==========");
            System.out.println(promptText);
            System.out.println("=============================================================");
            
            // Step 4: send to Django AI Service
            String generatedCode = djangoAiClient.sendForGeneration(projectId, promptText);
 
            return new GenerationResponse(projectId, mode, generatedCode, "SUCCESS", "Generation completed");
 
        } catch (Exception e) {
            return new GenerationResponse(projectId, null, null, "FAILED", e.getMessage());
        }
    }
}
