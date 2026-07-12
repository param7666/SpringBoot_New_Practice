package com.tcs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiRequest {

	private String model;
	
	private List<Message> messages;
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Message{
		
		private String role;
		private String content;
	}
}
