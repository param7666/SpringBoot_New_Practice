package com.tcs.repository;


import com.tcs.entity.GenerationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface GenerationRequestRepository extends JpaRepository<GenerationRequestEntity, UUID> {

    // For "my generation history" style endpoints, and for ownership checks.
    List<GenerationRequestEntity> findByUserId(Long userId);
}
