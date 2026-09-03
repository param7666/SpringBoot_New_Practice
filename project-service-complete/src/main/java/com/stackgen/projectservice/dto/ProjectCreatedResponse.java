package com.stackgen.projectservice.dto;

import com.stackgen.projectservice.entity.GenerationStatus;
import java.util.UUID;

public record ProjectCreatedResponse(
        UUID projectId,
        UUID generationId,
        GenerationStatus status
) {}
