package com.tcs.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tcs.client.DjangoAiClient;
import com.tcs.entity.FrontendFormDetailsEntity;
import com.tcs.entity.GenerationRequestEntity;
import com.tcs.enums.GenerationStatus;
import com.tcs.repository.FrontendFormDetailsRepository;
import com.tcs.repository.GenerationRequestRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class GenerationWorkerService {

    private final GenerationRequestRepository generationRequestRepository;
    private final FrontendFormDetailsRepository frontendFormDetailsRepository;
    private final DjangoAiClient djangoAiClient;

    public GenerationWorkerService(GenerationRequestRepository generationRequestRepository,
                                    FrontendFormDetailsRepository frontendFormDetailsRepository,
                                    DjangoAiClient djangoAiClient) {
        this.generationRequestRepository = generationRequestRepository;
        this.frontendFormDetailsRepository = frontendFormDetailsRepository;
        this.djangoAiClient = djangoAiClient;
    }

    /**
     * Runs on a separate thread (see AsyncConfig's "generationTaskExecutor").
     * The original HTTP request has already returned 202 by the time this runs.
     */
    @Async("generationTaskExecutor")
    public void processGeneration(UUID generationId) {
        GenerationRequestEntity job = generationRequestRepository.findById(generationId)
                .orElse(null);

        if (job == null) {
            return; // nothing to do — shouldn't happen in practice
        }

        job.setStatus(GenerationStatus.PROCESSING);
        generationRequestRepository.save(job);

        try {
            Optional<FrontendFormDetailsEntity> frontendDetails =
                    frontendFormDetailsRepository.findByGenerationRequestId(generationId);

            String frontendDetailsJson = frontendDetails
                    .map(this::combineFrontendJson)
                    .orElse(null);

            String resultJson = djangoAiClient.requestGeneration(
                    job.getProjectId(),
                    job.getDictionaryText(),
                    job.getTechStack(),
                    frontendDetailsJson,
                    job.getWireframePath()
            );
            System.out.println("========== RESPONSE FROM DJANGO AI SERVICE ==========");
            System.out.println(resultJson);
            System.out.println("======================================================");

            
            job.setResultJson(resultJson);
            job.setStatus(GenerationStatus.COMPLETED);


            
        } catch (Exception e) {
            job.setStatus(GenerationStatus.FAILED);
            job.setErrorMessage(e.getMessage());
        }

        generationRequestRepository.save(job);
    }

    // Combine the 4 stored JSON fragments back into one JSON object to send onward.
    private String combineFrontendJson(FrontendFormDetailsEntity details) {
        return String.format(
                "{\"pages\":%s,\"components\":%s,\"events\":%s,\"extraNotes\":%s}",
                nullToEmptyArray(details.getPagesJson()),
                nullToEmptyArray(details.getComponentsJson()),
                nullToEmptyArray(details.getEventsJson()),
                details.getExtraNotes() != null ? "\"" + escapeJson(details.getExtraNotes()) + "\"" : "null"
        );
    }

    private String nullToEmptyArray(String json) {
        return (json == null || json.isBlank()) ? "[]" : json;
    }

    private String escapeJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}