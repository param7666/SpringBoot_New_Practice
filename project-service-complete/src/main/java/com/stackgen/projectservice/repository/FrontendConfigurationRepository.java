package com.stackgen.projectservice.repository;

import com.stackgen.projectservice.entity.FrontendConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface FrontendConfigurationRepository extends JpaRepository<FrontendConfiguration, UUID> {
    Optional<FrontendConfiguration> findByProjectId(UUID projectId);
}
