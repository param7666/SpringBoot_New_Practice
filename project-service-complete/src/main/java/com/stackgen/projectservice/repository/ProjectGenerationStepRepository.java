package com.stackgen.projectservice.repository;

import com.stackgen.projectservice.entity.ProjectGenerationStep;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProjectGenerationStepRepository extends JpaRepository<ProjectGenerationStep, UUID> {
    List<ProjectGenerationStep> findByGenerationIdOrderByStartedAtAsc(UUID generationId);
}
