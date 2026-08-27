package com.tcs.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(GenerationWorkerService.class);

    // Key Django is expected to return the refined/updated dictionary under.
    private static final String UPDATED_DICTIONARY_KEY = "updatedDictionary";

    private final GenerationRequestRepository generationRequestRepository;
    private final FrontendFormDetailsRepository frontendFormDetailsRepository;
    private final DjangoAiClient djangoAiClient;
    private final ObjectMapper objectMapper;

    public GenerationWorkerService(GenerationRequestRepository generationRequestRepository,
                                    FrontendFormDetailsRepository frontendFormDetailsRepository,
                                    DjangoAiClient djangoAiClient,
                                    ObjectMapper objectMapper) {
        this.generationRequestRepository = generationRequestRepository;
        this.frontendFormDetailsRepository = frontendFormDetailsRepository;
        this.djangoAiClient = djangoAiClient;
        this.objectMapper = objectMapper;
    }

    @Async("generationTaskExecutor")
    public void processGeneration(UUID generationId) {
        GenerationRequestEntity job = generationRequestRepository.findById(generationId).orElse(null);
        if (job == null) {
            log.warn("processGeneration called for missing job id={}", generationId);
            return;
        }

        job.setStatus(GenerationStatus.PROCESSING);
        generationRequestRepository.save(job);

        try {
            Optional<FrontendFormDetailsEntity> frontendDetails =
                    frontendFormDetailsRepository.findByGenerationRequestId(generationId);

            String frontendDetailsJson = frontendDetails.isPresent()
                    ? objectMapper.writeValueAsString(combineFrontendJson(frontendDetails.get()))
                    : null;

            String resultJson = djangoAiClient.requestGeneration(
                    job.getProjectId(),
                    job.getDictionaryText(),
                    job.getTechStack(),
                    frontendDetailsJson,
                    job.getWireframePath()
            );

            log.debug("Django AI response for job {}: {}", generationId, resultJson);

            job.setResultJson(resultJson);
            job.setUpdatedDictionaryText(extractUpdatedDictionary(resultJson));
            job.setStatus(GenerationStatus.COMPLETED);

        } catch (Exception e) {
            log.error("Generation failed for job {}", generationId, e);
            job.setStatus(GenerationStatus.FAILED);
            job.setErrorMessage(e.getMessage());
        }

        generationRequestRepository.save(job);
    }

    private String extractUpdatedDictionary(String resultJson) {
        if (resultJson == null || resultJson.isBlank()) {
            return null;
        }
        try {
            JsonNode root = objectMapper.readTree(resultJson);
            JsonNode updatedDictionary = root.get(UPDATED_DICTIONARY_KEY);
            return updatedDictionary != null ? updatedDictionary.toString() : null;
        } catch (Exception e) {
            log.warn("Could not extract '{}' from Django response: {}", UPDATED_DICTIONARY_KEY, e.getMessage());
            return null;
        }
    }

    private ObjectNode combineFrontendJson(FrontendFormDetailsEntity details) throws Exception {
        ObjectNode node = objectMapper.createObjectNode();
        node.set("pages", parseOrEmptyArray(details.getPagesJson()));
        node.set("components", parseOrEmptyArray(details.getComponentsJson()));
        node.set("events", parseOrEmptyArray(details.getEventsJson()));
        node.put("extraNotes", details.getExtraNotes());
        return node;
    }

    private JsonNode parseOrEmptyArray(String json) throws Exception {
        if (json == null || json.isBlank()) {
            return objectMapper.createArrayNode();
        }
        return objectMapper.readTree(json);
    }
}
