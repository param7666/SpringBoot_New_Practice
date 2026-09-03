package com.stackgen.projectservice.dto;

import com.stackgen.projectservice.entity.*;
import java.time.LocalDateTime;

public record GenerationStepResponse(
        GenerationStepName stepName,
        StepStatus status,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        String errorMessage
) {}
