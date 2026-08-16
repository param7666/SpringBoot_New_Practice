package com.tcs.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.GenerationRequestEntity;

import java.util.UUID;

public interface GenerationRequestRepository extends JpaRepository<GenerationRequestEntity, UUID> {
}
