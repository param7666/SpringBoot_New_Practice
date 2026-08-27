package com.tcs.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tcs.dto.ProjectGenerationResponse;

import java.time.Duration;
import java.util.UUID;

/**
 * Forwards the raw AI-generated spec JSON, unparsed, to the Project
 * Generator service — same "extract once, forward as-is" pattern used
 * for dictionaryText and the wireframe file. This service has no need
 * to work with individual spec fields, so no parsing happens here.
 */
@Service
public class ProjectGeneratorClient {

    private final WebClient webClient;

    public ProjectGeneratorClient(
            WebClient.Builder webClientBuilder,
            @Value("${project-generator-service.base-url:http://project-generator-service}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public ProjectGenerationResponse generateProject(String aiSpecJson) {
        return webClient.post()
                .uri("/api/project-generator/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(aiSpecJson) // raw JSON string, forwarded as-is
                .retrieve()
                .bodyToMono(ProjectGenerationResponse.class)
                .timeout(Duration.ofMinutes(2))
                .block();
    }

    public byte[] downloadProject(UUID projectId) {
        return webClient.get()
                .uri("/api/project-generator/{projectId}/download", projectId)
                .retrieve()
                .bodyToMono(byte[].class)
                .timeout(Duration.ofMinutes(2))
                .block();
    }
}