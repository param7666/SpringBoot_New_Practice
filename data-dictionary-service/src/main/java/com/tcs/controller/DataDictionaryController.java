package com.tcs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.dto.FrontendFormRequest;
import com.tcs.dto.GenerationAcceptedResponse;
import com.tcs.dto.GenerationStatusResponse;
import com.tcs.service.GenerationIntakeService;
import com.tcs.service.GenerationStatusService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RestController
@RequestMapping("/api/data-dictionary")
@CrossOrigin(origins = "*") // relax for local testing only — restrict before production
@RequiredArgsConstructor
public class DataDictionaryController {

    private final GenerationIntakeService generationIntakeService;
    private final GenerationStatusService generationStatusService;



    /**
     * Kicks off generation and returns immediately (HTTP 202) with a
     * generationId. Does NOT wait for the AI response.
     *
     * frontendDetails is sent as a JSON part (Content-Type: application/json
     * on that part) within the multipart/form-data request.
     */
    @PostMapping(value = "/generate", consumes = "multipart/form-data")
    public ResponseEntity<GenerationAcceptedResponse> generate(
            @RequestParam("dataDictionaryFile") MultipartFile dataDictionaryFile,
            @RequestParam(value = "wireframeFile", required = false) MultipartFile wireframeFile,
            @RequestParam("techStack") String techStack,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestPart(value = "frontendDetails", required = false) FrontendFormRequest frontendDetails
    ) throws Exception {
        GenerationAcceptedResponse response = generationIntakeService.startGeneration(
                dataDictionaryFile, wireframeFile, techStack, projectId, frontendDetails);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /**
     * Poll this to check job status. Once status is COMPLETED, "result"
     * contains the structured JSON returned by Django (entities, apis,
     * services, roles, frontend, config — ready to feed into templates).
     */
    @GetMapping("/generations/{generationId}")
    public ResponseEntity<GenerationStatusResponse> getStatus(@PathVariable UUID generationId) throws Exception {
        return ResponseEntity.ok(generationStatusService.getStatus(generationId));
    }
}
