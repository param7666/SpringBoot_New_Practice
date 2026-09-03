package com.stackgen.projectservice.controller;

import com.stackgen.projectservice.entity.Project;
import com.stackgen.projectservice.service.ProjectService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class DownloadController {

    private final ProjectService projectService;

    public DownloadController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{projectId}/download")
    public ResponseEntity<Resource> download(
            @RequestParam UUID userId,
            @PathVariable UUID projectId) {

        Project project = projectService.getProject(projectId);

        if (!project.getOwnerId().equals(userId)) {
            throw new SecurityException("You do not have access to this project");
        }
        if (project.getZipFileKey() == null) {
            throw new IllegalStateException("ZIP is not available yet");
        }

        Resource resource = new FileSystemResource(Path.of(project.getZipFileKey()));

        if (!resource.exists()) {
            throw new IllegalStateException("ZIP file not found");
        }

        String fileName = project.getZipFileName() == null
                ? "project.zip"
                : project.getZipFileName();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(fileName).build().toString())
                .body(resource);
    }
}
