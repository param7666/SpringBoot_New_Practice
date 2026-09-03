package com.stackgen.projectservice.dto;

import com.stackgen.projectservice.entity.*;
import java.util.UUID;

public record ProjectDetailsResponse(
        UUID id,
        UUID ownerId,
        String name,
        String description,
        ProjectStatus status,
        GenerationType generationType,
        String frontendStack,
        String backendStack,
        GenerationStatus generationStatus,
        Integer progress,
        GenerationStepName currentStep,
        String errorMessage,
        boolean dataDictionaryUploaded,
        boolean frontendJsonUploaded,
        boolean zipAvailable
) {}
