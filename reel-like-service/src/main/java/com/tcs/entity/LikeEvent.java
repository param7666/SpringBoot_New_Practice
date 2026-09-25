package com.tcs.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="likes", 
uniqueConstraints = @UniqueConstraint(name="uk_reel_user",
						columnNames = {"reel_id","user_id"}))

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeEvent {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "reel_id", nullable = false)
	private Long reelId;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	private LikeStatus status;
	@Column(name = "client_timestamp", nullable = false)
	private Long clientTimestamp;
	
	@Column(name="updated_at", nullable = false)
	private Instant updatedAt;
}
