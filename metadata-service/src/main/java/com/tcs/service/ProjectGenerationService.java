package com.tcs.service;

import com.tcs.client.ProjectGeneratorClient;
import com.tcs.dto.ProjectGenerationResponse;
import com.tcs.entity.GenerationRequestEntity;
import com.tcs.enums.GenerationStatus;
import com.tcs.repository.GenerationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectGenerationService {

    private final GenerationRequestRepository generationRequestRepository;
    private final ProjectGeneratorClient projectGeneratorClient;

    public byte[] getOrBuildProjectZip(UUID generationId, Long requesterUserId, boolean isAdmin) {
        GenerationRequestEntity job = generationRequestRepository.findById(generationId)
                .orElseThrow(() -> new NoSuchElementException("No generation found with id " + generationId));

        if (!isAdmin && !job.getUserId().equals(requesterUserId)) {
            throw new SecurityException("You do not have access to this generation request");
        }

        if (job.getStatus() != GenerationStatus.COMPLETED) {
            throw new IllegalStateException("Generation is not completed yet — current status: " + job.getStatus());
        }

        if (job.getProjectGeneratorId() == null) {
            ProjectGenerationResponse response = projectGeneratorClient.generateProject(job.getResultJson());
            job.setProjectGeneratorId(response.getProjectId());
            generationRequestRepository.save(job);
        }

        return projectGeneratorClient.downloadProject(job.getProjectGeneratorId());
    }
}