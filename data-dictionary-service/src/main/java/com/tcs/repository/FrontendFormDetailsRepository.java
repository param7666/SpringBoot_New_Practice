package com.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.FrontendFormDetailsEntity;

import java.util.Optional;
import java.util.UUID;

public interface FrontendFormDetailsRepository extends JpaRepository<FrontendFormDetailsEntity, UUID> {
    Optional<FrontendFormDetailsEntity> findByGenerationRequestId(UUID generationRequestId);
}
