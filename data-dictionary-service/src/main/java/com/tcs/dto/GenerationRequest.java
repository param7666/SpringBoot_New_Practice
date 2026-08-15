package com.tcs.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerationRequest {

	@NotNull(message = "Data dictionary file is required")
	private MultipartFile dataDictionaryFile;
	
	private MultipartFile wireframeFile;
	
	@NotBlank(message = "Tech Stack is required")
	private String techStack;
	
	private String extraDetails;
	
	private String projectId;
}
