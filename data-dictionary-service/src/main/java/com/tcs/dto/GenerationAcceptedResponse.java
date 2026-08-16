package com.tcs.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.tcs.enums.GenerationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerationAcceptedResponse {
    private UUID generationId;
    private GenerationStatus status;
    private String message;
}
