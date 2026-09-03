package com.stackgen.projectservice.dto;

import com.stackgen.projectservice.entity.*;
import java.util.List;
import java.util.UUID;

public record GenerationStatusResponse(
        UUID projectId,
        UUID generationId,
        GenerationStatus status,
        Integer progress,
        GenerationStepName currentStep,
        String errorMessage,
        List<GenerationStepResponse> steps
) {}
