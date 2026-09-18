package com.tcs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.dto.ProjectGenerationResponse;
import com.tcs.service.ProjectBuilderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/project-generator")
@RequiredArgsConstructor
public class ProjectGeneratorController {

    private final ProjectBuilderService projectBuilderService;

    /**
     * Accepts the raw AI spec JSON as-is (metadata service forwards it
     * untouched), builds the project, stores the zip, returns an id.
     */
    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectGenerationResponse> generate(@RequestBody String rawSpecJson) {
        ProjectGenerationResponse response = projectBuilderService.build(rawSpecJson);

        HttpStatus status = "COMPLETED".equals(response.getStatus())
                ? HttpStatus.OK
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/{projectId}/download")
    public ResponseEntity<byte[]> download(@PathVariable UUID projectId) {
        byte[] zip = projectBuilderService.getZip(projectId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"project.zip\"")
                .body(zip);
    }
}
