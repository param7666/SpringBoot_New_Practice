package com.tcs.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.dto.ProjectGenerationResponse;
import com.tcs.dto.AiGeneratedSpec;
import com.tcs.dto.EntitySpec;
import com.tcs.dto.GenerationRules;
import com.tcs.entity.GeneratedProjectEntity;
import com.tcs.repository.GeneratedProjectRepository;
import com.tcs.util.ZipUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectBuilderService {

    private static final Logger log = LoggerFactory.getLogger(ProjectBuilderService.class);

    private final SpringInitializrClient springInitializrClient;
    private final JavaCodeTemplateService templateService;
    private final GeneratedProjectRepository generatedProjectRepository;
    private final ObjectMapper objectMapper;

    /**
     * @param rawSpecJson the raw AI spec JSON, exactly as forwarded by the metadata service
     */
    public ProjectGenerationResponse build(String rawSpecJson) {
        Path tempDir = null;
        try {
            AiGeneratedSpec spec = objectMapper.readValue(rawSpecJson, AiGeneratedSpec.class);

            String packageName = spec.getProjectConfiguration().getProject().getPackageName();
            GenerationRules rules = spec.getApplicationSpecification().getGenerationRules();

            // 1. Get empty project from Spring Initializr
            byte[] emptyProjectZip = springInitializrClient.downloadEmptyProject(
                    spec.getProjectConfiguration().getProject(),
                    spec.getProjectConfiguration().getDependencies()
            );

            // 2. Unzip into a temp working directory
            tempDir = ZipUtils.unzipToTempDir(emptyProjectZip, "project-build-");

            String basePackagePath = "src/main/java/" + packageName.replace(".", "/");

            // 3. Generate files for each entity, per generationRules
            for (EntitySpec entity : spec.getApplicationSpecification().getEntities()) {
                if (rules == null || rules.isGenerateEntity()) {
                    writeFile(tempDir, basePackagePath + "/entity/" + entity.getName() + ".java",
                            templateService.buildEntityFile(templateService.loadTemplate("entity.template"), packageName, entity));
                }
                if (rules == null || rules.isGenerateRepository()) {
                    writeFile(tempDir, basePackagePath + "/repository/" + entity.getName() + "Repository.java",
                            templateService.buildRepositoryFile(templateService.loadTemplate("repository.template"), packageName, entity));
                }
                if (rules == null || rules.isGenerateService()) {
                    writeFile(tempDir, basePackagePath + "/service/" + entity.getName() + "Service.java",
                            templateService.buildServiceFile(templateService.loadTemplate("service.template"), packageName, entity));
                }
                if (rules == null || rules.isGenerateController()) {
                    writeFile(tempDir, basePackagePath + "/controller/" + entity.getName() + "Controller.java",
                            templateService.buildControllerFile(templateService.loadTemplate("controller.template"), packageName, entity));
                }
                if (rules == null || rules.isGenerateDto()) {
                    writeFile(tempDir, basePackagePath + "/dto/" + entity.getName() + "Dto.java",
                            templateService.buildDtoFile(templateService.loadTemplate("dto.template"), packageName, entity));
                }
            }

            // 4. Re-zip the whole project
            byte[] finalZip = ZipUtils.zipDirectory(tempDir);

            // 5. Store in DB
            GeneratedProjectEntity savedProject = new GeneratedProjectEntity();
            savedProject.setProjectName(spec.getProjectConfiguration().getProject().getName());
            savedProject.setZipData(finalZip);
            savedProject.setStatus("COMPLETED");
            generatedProjectRepository.save(savedProject);

            return new ProjectGenerationResponse(savedProject.getId(), "COMPLETED", "Project generated successfully");

        } catch (Exception e) {
            log.error("Project build failed", e);

            GeneratedProjectEntity failed = new GeneratedProjectEntity();
            failed.setProjectName("unknown");
            failed.setZipData(new byte[0]);
            failed.setStatus("FAILED");
            failed.setErrorMessage(e.getMessage());
            generatedProjectRepository.save(failed);

            return new ProjectGenerationResponse(failed.getId(), "FAILED", e.getMessage());

        } finally {
            if (tempDir != null) {
                try {
                    ZipUtils.deleteRecursively(tempDir);
                } catch (Exception cleanupEx) {
                    log.warn("Failed to clean up temp dir {}", tempDir, cleanupEx);
                }
            }
        }
    }

    public byte[] getZip(UUID projectId) {
        return generatedProjectRepository.findById(projectId)
                .map(GeneratedProjectEntity::getZipData)
                .orElseThrow(() -> new java.util.NoSuchElementException("No generated project found for id " + projectId));
    }

    private void writeFile(Path baseDir, String relativePath, String content) throws Exception {
        Path target = baseDir.resolve(relativePath);
        Files.createDirectories(target.getParent());
        Files.writeString(target, content);
    }
}
