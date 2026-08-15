package com.tcs.dto;

import com.tcs.enums.RequestMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationResponse {

	private String projectId;
	
	private RequestMode usedMode;
	
	private String generatedCode;
	
	private String status;
	
	private String message;
}
