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
 * Runs synchronously as part of the original HTTP request — fast (parsing
 * text, saving a file, writing DB rows). The slow part (calling Django) is
 * handed off to GenerationWorkerService.
 */
@Service
@RequiredArgsConstructor
public class GenerationIntakeService {

    private final DataDictionaryParserService parserService;
    private final WireframeStorageService wireframeStorageService;
    private final GenerationRequestRepository generationRequestRepository;
    private final FrontendFormDetailsRepository frontendFormDetailsRepository;
    private final GenerationWorkerService generationWorkerService;
    private final ObjectMapper objectMapper;

    public GenerationAcceptedResponse startGeneration(
            Long userId,
            MultipartFile dataDictionaryFile,
            MultipartFile wireframeFile,
            String techStack,
            String projectId,
            FrontendFormRequest frontendDetails) throws Exception {

        String dictionaryText = parserService.parse(dataDictionaryFile);

        GenerationRequestEntity job = new GenerationRequestEntity();
        job.setUserId(userId);
        job.setProjectId(projectId);
        job.setTechStack(techStack);
        job.setDictionaryText(dictionaryText);
        job.setStatus(GenerationStatus.QUEUED);
        job = generationRequestRepository.save(job);

        if (wireframeFile != null && !wireframeFile.isEmpty()) {
            String path = wireframeStorageService.store(wireframeFile, job.getId());
            job.setWireframePath(path);
            job = generationRequestRepository.save(job);
        }

        if (frontendDetails != null) {
            FrontendFormDetailsEntity details = new FrontendFormDetailsEntity();
            details.setGenerationRequestId(job.getId());
            details.setPagesJson(objectMapper.writeValueAsString(frontendDetails.getPages()));
            details.setComponentsJson(objectMapper.writeValueAsString(frontendDetails.getComponents()));
            details.setEventsJson(objectMapper.writeValueAsString(frontendDetails.getEvents()));
            details.setExtraNotes(frontendDetails.getExtraNotes());
            frontendFormDetailsRepository.save(details);
        }

        generationWorkerService.processGeneration(job.getId());

        return new GenerationAcceptedResponse(job.getId(), GenerationStatus.QUEUED,
                "Generation started. Poll GET /api/data-dictionary/{generationId} for status.");
    }
}
