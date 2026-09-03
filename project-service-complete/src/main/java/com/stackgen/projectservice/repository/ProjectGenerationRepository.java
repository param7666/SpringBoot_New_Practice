package com.stackgen.projectservice.repository;

import com.stackgen.projectservice.entity.ProjectGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProjectGenerationRepository extends JpaRepository<ProjectGeneration, UUID> {
    Optional<ProjectGeneration> findByProjectId(UUID projectId);
}
