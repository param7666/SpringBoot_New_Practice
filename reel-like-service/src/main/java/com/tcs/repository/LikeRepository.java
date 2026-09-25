package com.tcs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tcs.entity.LikeEvent;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;

public interface LikeRepository extends JpaRepository<LikeEvent, Long> {

	Optional<LikeEvent> findByReelIdAndUserId(Long reelId,Long userId);
	
	@Modifying
    @Transactional
    @Query(value = """
        INSERT INTO likes (reel_id, user_id, status, client_timestamp, updated_at)
        VALUES (:reelId, :userId, :status, :clientTimestamp, now())
        ON CONFLICT ON CONSTRAINT uk_reel_user
        DO UPDATE SET
            status = EXCLUDED.status,
            client_timestamp = EXCLUDED.client_timestamp,
            updated_at = now()
        WHERE likes.client_timestamp < EXCLUDED.client_timestamp
        """, nativeQuery = true)
	
	void upsertLike(
			@Param("reelId") Long reelId, 
			@Param("userId") Long userId,
			@Param("status")String status,
			@Param("clientTimestamp") Long clientTimestamp);
}
