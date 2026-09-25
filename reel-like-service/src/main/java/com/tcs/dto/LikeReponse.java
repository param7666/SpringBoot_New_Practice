package com.tcs.dto;

public record LikeReponse(
	
	Long reelId,
	boolean liked,
	long likeCount,
	boolean duplicate
	) {

}
