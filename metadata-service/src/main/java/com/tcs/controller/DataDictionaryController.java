  package com.tcs.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tcs.dto.FrontendFormRequest;
import com.tcs.dto.GenerationAcceptedResponse;
import com.tcs.dto.GenerationStatusResponse;
import com.tcs.service.GenerationIntakeService;
import com.tcs.service.GenerationStatusService;
import com.tcs.service.ProjectGenerationService;
import org.springframework.http.HttpHeaders;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Ownership is enforced via the JWT — X-Auth-UserId and X-Auth-Role are
 * added by the API Gateway after it validates the token (see
 * AuthenticationFilter in the gateway module). This service sits behind
 * the gateway, so these headers are trusted here.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // relax for local testing only — restrict before production
@RequiredArgsConstructor
public class DataDictionaryController {

    private final GenerationIntakeService generationIntakeService;
    private final GenerationStatusService generationStatusService;
    private final ProjectGenerationService projectGenerationService;

    @PostMapping(value = "/generate", consumes = "multipart/form-data")
    public ResponseEntity<GenerationAcceptedResponse> generate(
            @RequestHeader("X-Auth-UserId") Long userId,
            @RequestParam("dataDictionaryFile") MultipartFile dataDictionaryFile,
            @RequestParam(value = "wireframeFile", required = false) MultipartFile wireframeFile,
            @RequestParam("techStack") String techStack,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestPart(value = "frontendDetails", required = false) FrontendFormRequest frontendDetails
    ) throws Exception {
        GenerationAcceptedResponse response = generationIntakeService.startGeneration(
                userId, dataDictionaryFile, wireframeFile, techStack, projectId, frontendDetails);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/generations/{generationId}")
    public ResponseEntity<GenerationStatusResponse> getStatus(
            @PathVariable UUID generationId,
            @RequestHeader("X-Auth-UserId") Long userId,
            @RequestHeader(value = "X-Auth-Role", required = false) String role
    ) {
        boolean isAdmin = "ADMIN".equalsIgnoreCase(role);
        return ResponseEntity.ok(generationStatusService.getStatus(generationId, userId, isAdmin));
    }
    
    @GetMapping("/generations/{generationId}/download-project")
    public ResponseEntity<byte[]> downloadProject(
            @PathVariable UUID generationId,
            @RequestHeader("X-Auth-UserId") Long userId,
            @RequestHeader(value = "X-Auth-Role", required = false) String role
    ) throws Exception {
        boolean isAdmin = "ADMIN".equalsIgnoreCase(role);
        byte[] zipBytes = projectGenerationService.getOrBuildProjectZip(generationId, userId, isAdmin);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"project.zip\"")
                .body(zipBytes);
    }
}
