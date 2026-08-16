package com.tcs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.dto.FrontendFormRequest;
import com.tcs.dto.GenerationAcceptedResponse;
import com.tcs.entity.FrontendFormDetailsEntity;
import com.tcs.entity.GenerationRequestEntity;
import com.tcs.enums.GenerationStatus;
import com.tcs.repository.FrontendFormDetailsRepository;
import com.tcs.repository.GenerationRequestRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Everything here runs SYNCHRONOUSLY, as part of the original HTTP request
 * — but it's all fast (parsing text, saving a file, writing DB rows), so
 * the request still returns quickly. The slow part (calling Django/AI) is
 * handed off to GenerationWorkerService and does NOT happen here.
 */
@Service
@RequiredArgsConstructor
public class GenerationIntakeService {

    private final DataDictionaryParserService parserService;
    private final WireframeStorageService wireframeStorageService;
    private final GenerationRequestRepository generationRequestRepository;
    private final FrontendFormDetailsRepository frontendFormDetailsRepository;
    private final GenerationWorkerService generationWorkerService;
    private final ObjectMapper objectMapper = new ObjectMapper();



    public GenerationAcceptedResponse startGeneration(
            MultipartFile dataDictionaryFile,
            MultipartFile wireframeFile,
            String techStack,
            String projectId,
            FrontendFormRequest frontendDetails) throws Exception {

        // 1. Parse the dictionary (fast — all sheets, raw text)
        String dictionaryText = parserService.parse(dataDictionaryFile);

        // 2. Create the job record first (status QUEUED) so we have an ID
        GenerationRequestEntity job = new GenerationRequestEntity();
        job.setProjectId(projectId);
        job.setTechStack(techStack);
        job.setDictionaryText(dictionaryText);
        job.setStatus(GenerationStatus.QUEUED);
        job = generationRequestRepository.save(job);

        // 3. Save wireframe to disk immediately, if provided (fast — just bytes to disk)
        if (wireframeFile != null && !wireframeFile.isEmpty()) {
            String path = wireframeStorageService.store(wireframeFile, job.getId());
            job.setWireframePath(path);
            job = generationRequestRepository.save(job);
        }

        // 4. Save the frontend form details, if provided
        if (frontendDetails != null) {
            FrontendFormDetailsEntity details = new FrontendFormDetailsEntity();
            details.setGenerationRequestId(job.getId());
            details.setPagesJson(objectMapper.writeValueAsString(frontendDetails.getPages()));
            details.setComponentsJson(objectMapper.writeValueAsString(frontendDetails.getComponents()));
            details.setEventsJson(objectMapper.writeValueAsString(frontendDetails.getEvents()));
            details.setExtraNotes(frontendDetails.getExtraNotes());
            frontendFormDetailsRepository.save(details);
        }

        // 5. Kick off the background job — returns immediately, does NOT block this request
        generationWorkerService.processGeneration(job.getId());

        // 6. Respond right away with the "ticket number"
        return new GenerationAcceptedResponse(job.getId(), GenerationStatus.QUEUED,
                "Generation started. Poll GET /api/data-dictionary/generations/{generationId} for status.");
    }
}
