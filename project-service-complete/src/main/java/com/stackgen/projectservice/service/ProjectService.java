package com.stackgen.projectservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackgen.projectservice.dto.*;
import com.stackgen.projectservice.entity.*;
import com.stackgen.projectservice.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TechStackRepository techStackRepository;
    private final ProjectTechSelectionRepository selectionRepository;
    private final DataDictionaryUploadRepository dictionaryRepository;
    private final FrontendConfigurationRepository frontendRepository;
    private final ProjectGenerationRepository generationRepository;
    private final ProjectGenerationStepRepository stepRepository;
    private final GenerationWorkerService workerService;
    private final ObjectMapper objectMapper;
    private final Path uploadDir;

    public ProjectService(
            ProjectRepository projectRepository,
            TechStackRepository techStackRepository,
            ProjectTechSelectionRepository selectionRepository,
            DataDictionaryUploadRepository dictionaryRepository,
            FrontendConfigurationRepository frontendRepository,
            ProjectGenerationRepository generationRepository,
            ProjectGenerationStepRepository stepRepository,
            GenerationWorkerService workerService,
            ObjectMapper objectMapper,
            @Value("${storage.upload-dir}") String uploadDir) {
        this.projectRepository = projectRepository;
        this.techStackRepository = techStackRepository;
        this.selectionRepository = selectionRepository;
        this.dictionaryRepository = dictionaryRepository;
        this.frontendRepository = frontendRepository;
        this.generationRepository = generationRepository;
        this.stepRepository = stepRepository;
        this.workerService = workerService;
        this.objectMapper = objectMapper;
        this.uploadDir = Paths.get(uploadDir);
    }

    @Transactional
    public ProjectCreatedResponse createProject(
            CreateProjectRequest request,
            MultipartFile dataDictionary,
            String frontendJson) throws IOException {

        validateGenerationInput(request.generationType(), request.frontendStackId(),
                request.backendStackId(), dataDictionary, frontendJson);

        Project project = new Project();
        project.setOwnerId(request.userId());
        project.setName(request.name());
        project.setDescription(request.description());
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project = projectRepository.save(project);

        ProjectTechSelection selection = new ProjectTechSelection();
        selection.setProject(project);
        selection.setGenerationType(request.generationType());

        if (request.frontendStackId() != null) {
            selection.setFrontendStack(techStackRepository.findById(request.frontendStackId())
                    .orElseThrow(() -> new IllegalArgumentException("Frontend tech stack not found")));
        }
        if (request.backendStackId() != null) {
            selection.setBackendStack(techStackRepository.findById(request.backendStackId())
                    .orElseThrow(() -> new IllegalArgumentException("Backend tech stack not found")));
        }
        selectionRepository.save(selection);

        if (dataDictionary != null && !dataDictionary.isEmpty()) {
            Files.createDirectories(uploadDir);
            Path projectDir = uploadDir.resolve(project.getId().toString());
            Files.createDirectories(projectDir);
            Path target = projectDir.resolve(cleanFileName(dataDictionary.getOriginalFilename()));
            Files.copy(dataDictionary.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            DataDictionaryUpload upload = new DataDictionaryUpload();
            upload.setProject(project);
            upload.setFileName(target.getFileName().toString());
            upload.setFilePath(target.toAbsolutePath().toString());
            dictionaryRepository.save(upload);
        }

        if (frontendJson != null && !frontendJson.isBlank()) {
            objectMapper.readTree(frontendJson); // validates JSON
            FrontendConfiguration config = new FrontendConfiguration();
            config.setProject(project);
            config.setFrontendJson(frontendJson);
            frontendRepository.save(config);
        }

        ProjectGeneration generation = new ProjectGeneration();
        generation.setProject(project);
        generation.setStatus(GenerationStatus.QUEUED);
        generation.setProgress(0);
        generation.setCurrentStep(GenerationStepName.PROJECT_CREATED);
        generation = generationRepository.save(generation);

        initializeSteps(generation);

        UUID generationId = generation.getId();
        UUID projectId = project.getId();

        // Do not let the async worker read partially committed data.
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                workerService.startGeneration(projectId, generationId);
            }
        });

        return new ProjectCreatedResponse(projectId, generationId, GenerationStatus.QUEUED);
    }

    private void validateGenerationInput(
            GenerationType type,
            UUID frontendStackId,
            UUID backendStackId,
            MultipartFile dataDictionary,
            String frontendJson) {

        if ((type == GenerationType.BACKEND || type == GenerationType.FULLSTACK)
                && (dataDictionary == null || dataDictionary.isEmpty())) {
            throw new IllegalArgumentException("Data dictionary is required for BACKEND/FULLSTACK");
        }

        if ((type == GenerationType.FRONTEND || type == GenerationType.FULLSTACK)
                && (frontendJson == null || frontendJson.isBlank())) {
            throw new IllegalArgumentException("Frontend JSON is required for FRONTEND/FULLSTACK");
        }

        if ((type == GenerationType.BACKEND || type == GenerationType.FULLSTACK)
                && backendStackId == null) {
            throw new IllegalArgumentException("Backend tech stack is required");
        }

        if ((type == GenerationType.FRONTEND || type == GenerationType.FULLSTACK)
                && frontendStackId == null) {
            throw new IllegalArgumentException("Frontend tech stack is required");
        }
    }

    private void initializeSteps(ProjectGeneration generation) {
        for (GenerationStepName stepName : GenerationStepName.values()) {
            ProjectGenerationStep step = new ProjectGenerationStep();
            step.setGeneration(generation);
            step.setStepName(stepName);
            step.setStatus(StepStatus.PENDING);
            stepRepository.save(step);
        }
    }

    @Transactional(readOnly = true)
    public List<ProjectSummaryResponse> getProjects(UUID userId) {
        return projectRepository.findByOwnerIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(p -> new ProjectSummaryResponse(p.getId(), p.getName(), p.getDescription(), p.getStatus()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectDetailsResponse getProjectDetails(UUID userId, UUID projectId) {
        Project project = getOwnedProject(userId, projectId);
        ProjectTechSelection selection = selectionRepository.findByProjectId(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Tech selection not found"));

        ProjectGeneration generation = generationRepository.findByProjectId(projectId).orElse(null);

        return new ProjectDetailsResponse(
                project.getId(),
                project.getOwnerId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                selection.getGenerationType(),
                selection.getFrontendStack() == null ? null : selection.getFrontendStack().getName(),
                selection.getBackendStack() == null ? null : selection.getBackendStack().getName(),
                generation == null ? null : generation.getStatus(),
                generation == null ? 0 : generation.getProgress(),
                generation == null ? null : generation.getCurrentStep(),
                generation == null ? null : generation.getErrorMessage(),
                dictionaryRepository.findByProjectId(projectId).isPresent(),
                frontendRepository.findByProjectId(projectId).isPresent(),
                project.getZipFileKey() != null
        );
    }

    private Project getOwnedProject(UUID userId, UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found"));
        if (!project.getOwnerId().equals(userId)) {
            throw new SecurityException("You do not have access to this project");
        }
        return project;
    }

    private String cleanFileName(String name) {
        if (name == null || name.isBlank()) return "data-dictionary.xlsx";
        return Paths.get(name).getFileName().toString().replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public Project getProject(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found"));
    }

    public ProjectGeneration getGeneration(UUID generationId) {
        return generationRepository.findById(generationId)
                .orElseThrow(() -> new NoSuchElementException("Generation not found"));
    }

    public Optional<DataDictionaryUpload> getDictionary(UUID projectId) {
        return dictionaryRepository.findByProjectId(projectId);
    }

    public Optional<FrontendConfiguration> getFrontend(UUID projectId) {
        return frontendRepository.findByProjectId(projectId);
    }

    public ProjectTechSelection getSelection(UUID projectId) {
        return selectionRepository.findByProjectId(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Tech selection not found"));
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public ProjectGenerationRepository getGenerationRepository() {
        return generationRepository;
    }

    public ProjectRepository getProjectRepository() {
        return projectRepository;
    }

    public ProjectGenerationStepRepository getStepRepository() {
        return stepRepository;
    }
}
