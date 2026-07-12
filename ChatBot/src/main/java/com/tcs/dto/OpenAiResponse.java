package com.tcs.dto;

import java.util.List;

import com.tcs.dto.OpenAiRequest.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiResponse {

	private List<Choice> choices;
	
	@Data
	public static class Choice{
		
		private Message message;
		
		@Data
		public static class Message{
			private String role;
			
			private String content;
		}
	}
}
