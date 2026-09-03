package com.stackgen.projectservice.repository;

import com.stackgen.projectservice.entity.ProjectTechSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProjectTechSelectionRepository extends JpaRepository<ProjectTechSelection, UUID> {
    Optional<ProjectTechSelection> findByProjectId(UUID projectId);
}
