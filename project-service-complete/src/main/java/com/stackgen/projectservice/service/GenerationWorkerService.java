package com.stackgen.projectservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackgen.projectservice.dto.PythonGenerationRequest;
import com.stackgen.projectservice.dto.PythonGenerationResponse;
import com.stackgen.projectservice.entity.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GenerationWorkerService {

    private final ProjectService projectService;
    private final WebClient metadataWebClient;
    private final WebClient pythonWebClient;
    private final ObjectMapper objectMapper;

    public GenerationWorkerService(
            ProjectService projectService,
            @Qualifier("metadataWebClient") WebClient metadataWebClient,
            @Qualifier("pythonWebClient") WebClient pythonWebClient,
            ObjectMapper objectMapper) {
        this.projectService = projectService;
        this.metadataWebClient = metadataWebClient;
        this.pythonWebClient = pythonWebClient;
        this.objectMapper = objectMapper;
    }

    @Async("generationTaskExecutor")
    public void startGeneration(UUID projectId, UUID generationId) {
        try {
            updateGeneration(generationId, GenerationStatus.IN_PROGRESS, 5, GenerationStepName.PROJECT_CREATED, null);
            completeStep(generationId, GenerationStepName.PROJECT_CREATED);

            Project project = projectService.getProject(projectId);
            ProjectTechSelection selection = projectService.getSelection(projectId);

            Object dataDictionary = null;
            if (selection.getGenerationType() == GenerationType.BACKEND ||
                    selection.getGenerationType() == GenerationType.FULLSTACK) {

                startStep(generationId, GenerationStepName.DATA_DICTIONARY_STORED, 10);
                completeStep(generationId, GenerationStepName.DATA_DICTIONARY_STORED);

                startStep(generationId, GenerationStepName.METADATA_EXTRACTION, 20);

                DataDictionaryUpload dictionary = projectService.getDictionary(projectId)
                        .orElseThrow(() -> new IllegalArgumentException("Data dictionary not found"));

                FileSystemResource fileResource = new FileSystemResource(dictionary.getFilePath());

                MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
                form.add("projectId", projectId.toString());
                form.add("userId", project.getOwnerId().toString());
                form.add("file", fileResource);

                String response = metadataWebClient.post()
                        .uri("/api/metadata/extract")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .bodyValue(form)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                dataDictionary = objectMapper.readTree(response);
                completeStep(generationId, GenerationStepName.METADATA_EXTRACTION);
            }

            Object frontendJson = null;
            if (selection.getGenerationType() == GenerationType.FRONTEND ||
                    selection.getGenerationType() == GenerationType.FULLSTACK) {

                startStep(generationId, GenerationStepName.FRONTEND_JSON_STORED, 15);
                FrontendConfiguration frontend = projectService.getFrontend(projectId)
                        .orElseThrow(() -> new IllegalArgumentException("Frontend JSON not found"));
                frontendJson = objectMapper.readTree(frontend.getFrontendJson());
                completeStep(generationId, GenerationStepName.FRONTEND_JSON_STORED);
            }

            startStep(generationId, GenerationStepName.AI_GENERATION, 45);

            PythonGenerationRequest.StackInfo frontendStack =
                    selection.getFrontendStack() == null ? null :
                            new PythonGenerationRequest.StackInfo(
                                    selection.getFrontendStack().getName(),
                                    selection.getFrontendStack().getLanguage(),
                                    selection.getFrontendStack().getVersion());

            PythonGenerationRequest.StackInfo backendStack =
                    selection.getBackendStack() == null ? null :
                            new PythonGenerationRequest.StackInfo(
                                    selection.getBackendStack().getName(),
                                    selection.getBackendStack().getLanguage(),
                                    selection.getBackendStack().getVersion());

            PythonGenerationRequest request = new PythonGenerationRequest(
                    projectId,
                    project.getOwnerId(),
                    selection.getGenerationType(),
                    frontendStack,
                    backendStack,
                    dataDictionary,
                    frontendJson
            );

            PythonGenerationResponse pythonResponse = pythonWebClient.post()
                    .uri("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PythonGenerationResponse.class)
                    .block();

            completeStep(generationId, GenerationStepName.AI_GENERATION);
            startStep(generationId, GenerationStepName.ZIP_GENERATION, 75);
            completeStep(generationId, GenerationStepName.ZIP_GENERATION);

            if (pythonResponse == null || pythonResponse.zipFileKey() == null) {
                throw new IllegalStateException("Python service did not return zipFileKey");
            }

            startStep(generationId, GenerationStepName.ZIP_STORED, 90);

            project.setZipFileKey(pythonResponse.zipFileKey());
            project.setZipFileName(
                    pythonResponse.zipFileName() == null
                            ? project.getName().replaceAll("\\s+", "_") + ".zip"
                            : pythonResponse.zipFileName());
            project.setStatus(ProjectStatus.GENERATED);
            projectService.getProjectRepository().save(project);

            completeStep(generationId, GenerationStepName.ZIP_STORED);
            startStep(generationId, GenerationStepName.COMPLETED, 100);
            completeStep(generationId, GenerationStepName.COMPLETED);

            updateGeneration(generationId, GenerationStatus.COMPLETED, 100,
                    GenerationStepName.COMPLETED, null);

        } catch (Exception ex) {
            failGeneration(generationId, ex.getMessage());
        }
    }

    private void updateGeneration(UUID generationId, GenerationStatus status, int progress,
                                  GenerationStepName currentStep, String error) {
        ProjectGeneration generation = projectService.getGeneration(generationId);
        generation.setStatus(status);
        generation.setProgress(progress);
        generation.setCurrentStep(currentStep);
        generation.setErrorMessage(error);
        if (status == GenerationStatus.IN_PROGRESS && generation.getStartedAt() == null) {
            generation.setStartedAt(LocalDateTime.now());
        }
        if (status == GenerationStatus.COMPLETED || status == GenerationStatus.FAILED) {
            generation.setCompletedAt(LocalDateTime.now());
        }
        projectService.getGenerationRepository().save(generation);
    }

    private void startStep(UUID generationId, GenerationStepName name, int progress) {
        ProjectGenerationStep step = findStep(generationId, name);
        step.setStatus(StepStatus.IN_PROGRESS);
        step.setStartedAt(LocalDateTime.now());
        projectService.getStepRepository().save(step);
        updateGeneration(generationId, GenerationStatus.IN_PROGRESS, progress, name, null);
    }

    private void completeStep(UUID generationId, GenerationStepName name) {
        ProjectGenerationStep step = findStep(generationId, name);
        step.setStatus(StepStatus.COMPLETED);
        step.setCompletedAt(LocalDateTime.now());
        projectService.getStepRepository().save(step);
    }

    private void failGeneration(UUID generationId, String error) {
        ProjectGeneration generation = projectService.getGeneration(generationId);
        generation.setStatus(GenerationStatus.FAILED);
        generation.setErrorMessage(error == null ? "Generation failed" : error);
        generation.setCompletedAt(LocalDateTime.now());
        projectService.getGenerationRepository().save(generation);

        Project project = generation.getProject();
        project.setStatus(ProjectStatus.FAILED);
        projectService.getProjectRepository().save(project);

        ProjectGenerationStep step = findStep(generationId, generation.getCurrentStep());
        step.setStatus(StepStatus.FAILED);
        step.setErrorMessage(error);
        step.setCompletedAt(LocalDateTime.now());
        projectService.getStepRepository().save(step);
    }

    private ProjectGenerationStep findStep(UUID generationId, GenerationStepName name) {
        return projectService.getStepRepository().findByGenerationIdOrderByStartedAtAsc(generationId)
                .stream()
                .filter(s -> s.getStepName() == name)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Generation step not found: " + name));
    }

}
