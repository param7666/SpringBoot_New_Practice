package com.stackgen.projectservice.controller;

import com.stackgen.projectservice.dto.*;
import com.stackgen.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProjectCreatedResponse createProject(
            @RequestPart("project") @Valid CreateProjectRequest request,
            @RequestPart(value = "dataDictionary", required = false) MultipartFile dataDictionary,
            @RequestPart(value = "frontendJson", required = false) String frontendJson) throws IOException {

        return projectService.createProject(request, dataDictionary, frontendJson);
    }

    @GetMapping
    public List<ProjectSummaryResponse> getProjects(@RequestParam UUID userId) {
        return projectService.getProjects(userId);
    }

    @GetMapping("/{projectId}")
    public ProjectDetailsResponse getProject(
            @RequestParam UUID userId,
            @PathVariable UUID projectId) {
        return projectService.getProjectDetails(userId, projectId);
    }
}
