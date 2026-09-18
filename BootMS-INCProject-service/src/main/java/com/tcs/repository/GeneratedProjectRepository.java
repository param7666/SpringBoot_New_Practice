package com.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.GeneratedProjectEntity;

import java.util.UUID;

public interface GeneratedProjectRepository extends JpaRepository<GeneratedProjectEntity, UUID> {
}
