package com.stackgen.projectservice.service;

import com.stackgen.projectservice.entity.Project;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.UUID;

@Service
public class DownloadService {

    private final ProjectService projectService;

    public DownloadService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Resource getZip(UUID projectId, UUID userId) {
        Project project = projectService.getProject(projectId);

        if (!project.getOwnerId().equals(userId)) {
            throw new SecurityException("You do not have access to this project");
        }
        if (project.getZipFileKey() == null) {
            throw new IllegalStateException("Project ZIP is not available yet");
        }

        FileSystemResource resource = new FileSystemResource(Path.of(project.getZipFileKey()));
        if (!resource.exists()) {
            throw new IllegalStateException("ZIP file not found");
        }
        return resource;
    }
}
