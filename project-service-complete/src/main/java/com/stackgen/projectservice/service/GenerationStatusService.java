package com.stackgen.projectservice.service;

import com.stackgen.projectservice.dto.*;
import com.stackgen.projectservice.entity.ProjectGeneration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GenerationStatusService {

    private final ProjectService projectService;

    public GenerationStatusService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Transactional(readOnly = true)
    public GenerationStatusResponse getStatus(UUID userId, UUID projectId) {
        projectService.getProjectDetails(userId, projectId);

        ProjectGeneration generation = projectService.getGenerationRepository()
                .findByProjectId(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Generation not found"));

        var steps = projectService.getStepRepository()
                .findByGenerationIdOrderByStartedAtAsc(generation.getId())
                .stream()
                .map(s -> new GenerationStepResponse(
                        s.getStepName(),
                        s.getStatus(),
                        s.getStartedAt(),
                        s.getCompletedAt(),
                        s.getErrorMessage()))
                .toList();

        return new GenerationStatusResponse(
                projectId,
                generation.getId(),
                generation.getStatus(),
                generation.getProgress(),
                generation.getCurrentStep(),
                generation.getErrorMessage(),
                steps
        );
    }
}
