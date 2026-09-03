package com.stackgen.projectservice.dto;

import com.stackgen.projectservice.entity.ProjectStatus;
import java.util.UUID;

public record ProjectSummaryResponse(
        UUID id,
        String name,
        String description,
        ProjectStatus status
) {}
