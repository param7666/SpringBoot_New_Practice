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
    private final ObjectMapper objectMapper;

    /**
     * @param requesterUserId the caller's userId, from the JWT
     * @param isAdmin         true if caller has ADMIN role — admins can view any job
     */
    public GenerationStatusResponse getStatus(UUID generationId, Long requesterUserId, boolean isAdmin) {
        GenerationRequestEntity job = generationRequestRepository.findById(generationId)
                .orElseThrow(() -> new NoSuchElementException("No generation found with id " + generationId));

        if (!isAdmin && !job.getUserId().equals(requesterUserId)) {
            throw new SecurityException("You do not have access to this generation request");
        }

        JsonNode result = safeReadTree(job.getResultJson());
        JsonNode updatedDictionary = safeReadTree(job.getUpdatedDictionaryText());

        return new GenerationStatusResponse(
                job.getId(),
                job.getStatus(),
                result,
                updatedDictionary,
                job.getErrorMessage(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }

    private JsonNode safeReadTree(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            return null;
        }
    }
}
