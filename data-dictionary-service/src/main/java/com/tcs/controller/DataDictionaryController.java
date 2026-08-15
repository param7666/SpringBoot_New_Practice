package com.tcs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.dto.GenerationRequest;
import com.tcs.dto.GenerationResponse;
import com.tcs.service.DataDictionaryOrchestrationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/data-dictionary")
@RequiredArgsConstructor
public class DataDictionaryController {

	private final DataDictionaryOrchestrationService service;
	
	
	@PostMapping(value = "/generate", consumes = "multipart/form-data")
	public ResponseEntity<?> generate(
			 @RequestParam("dataDictionaryFile") MultipartFile dataDictionaryFile,
	         @RequestParam(value = "wireframeFile", required = false) MultipartFile wireframeFile,
	         @RequestParam("techStack") String techStack,
	         @RequestParam(value = "extraDetails", required = false) String extraDetails,
	         @RequestParam(value = "projectId", required = false) String projectId) {
		
		System.out.println("DataDictionaryController.generate()");
		
		 GenerationRequest request = new GenerationRequest();
	     request.setDataDictionaryFile(dataDictionaryFile);
	     request.setWireframeFile(wireframeFile);
	     request.setTechStack(techStack);
	     request.setExtraDetails(extraDetails);
	     request.setProjectId(projectId);
	     
	     GenerationResponse response = service.process(request);
	     HttpStatus status = "FAILED".equals(response.getStatus())
	                ? HttpStatus.BAD_REQUEST
	                : HttpStatus.OK;
	 
	        return ResponseEntity.status(status).body(response);
	}
	
}
