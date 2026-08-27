package com.tcs.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.tcs.enums.GenerationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerationStatusResponse {
    private UUID generationId;
    private GenerationStatus status;
    private JsonNode result;
    private JsonNode updatedDictionary;
    private String errorMessage;
    private Instant createdAt;
    private Instant updatedAt;
}
