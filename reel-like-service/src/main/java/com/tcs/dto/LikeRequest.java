package com.tcs.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikeRequest(
		
	@NotNull(message = "Reel Id is required")
	@Positive(message = "Reel Id must be positive")
	Long reelId,
	
	@NotNull(message = "User Id is required")
	@Positive(message = "User Id must be positive")
	Long userId,
	
	@NotNull(message = "clientTimestamp is required")
	Long clientTimestamp
		
	) {

}
