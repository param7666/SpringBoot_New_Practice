package com.tcs.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.ChatRequest;
import com.tcs.dto.ChatResponse;
import com.tcs.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {

	private final ChatService service;
	
	@PostMapping
	public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest req) {
		String reply=service.getChatResponse(req.getMessage());
		return ResponseEntity.ok(new ChatResponse(reply));
	}
	
	
}
