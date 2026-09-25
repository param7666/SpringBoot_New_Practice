package com.tcs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.LikeReponse;
import com.tcs.dto.LikeRequest;
import com.tcs.service.LikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reels")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService service;
	
	 @PostMapping("/{reelId}/likes")
	 public ResponseEntity<LikeReponse> like(@PathVariable Long reelId,
			 									@Valid @RequestBody LikeRequest req) {
		 
		 LikeRequest resolved=
				 new LikeRequest(reelId, req.userId(), req.clientTimestamp());
		 
		 LikeReponse response=service.like(resolved);
		 return ResponseEntity.status(200).body(response);

	 }
}
