package com.stackgen.projectservice.dto;

import com.stackgen.projectservice.entity.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateProjectRequest(
        @NotNull UUID userId,
        @NotBlank String name,
        String description,
        @NotNull GenerationType generationType,
        UUID frontendStackId,
        UUID backendStackId
) {}
