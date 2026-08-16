package com.tcs.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.dto.GenerationStatusResponse;
import com.tcs.entity.GenerationRequestEntity;
import com.tcs.repository.GenerationRequestRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenerationStatusService {

    private final GenerationRequestRepository generationRequestRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public GenerationStatusResponse getStatus(UUID generationId) throws Exception {
        GenerationRequestEntity job = generationRequestRepository.findById(generationId)
                .orElseThrow(() -> new NoSuchElementException("No generation found with id " + generationId));

        JsonNode result = null;
        if (job.getResultJson() != null && !job.getResultJson().isBlank()) {
            try {
                result = objectMapper.readTree(job.getResultJson());
            } catch (Exception ignored) {
                // If Django's response wasn't valid JSON, leave result null;
                // the raw text is still visible via errorMessage/logs if needed.
            }
        }

        return new GenerationStatusResponse(
                job.getId(),
                job.getStatus(),
                result,
                job.getErrorMessage(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}
