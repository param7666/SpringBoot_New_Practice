package com.tcs.client;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * Forwards the raw, unprocessed pieces to Django AI Service — the parsed
 * dictionary text, the wireframe file itself (not a description), tech
 * stack, and structured frontend form details as JSON text. Django owns
 * all prompt-building, vision handling, and LLM calls.
 *
 * Called from a background (@Async) thread, so a long blocking call here
 * does NOT block any user-facing HTTP request.
 */
@Service
public class DjangoAiClient {

    private final WebClient webClient;

    public DjangoAiClient(WebClient.Builder webClientBuilder,
                           @Value("${django-ai-service.base-url:http://django-ai-service}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public String requestGeneration(String projectId, String dictionaryText, String techStack,
                                     String frontendDetailsJson, String wireframePath) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        if (projectId != null && !projectId.isBlank()) {
            builder.part("projectId", projectId);
        }
        builder.part("dictionaryText", dictionaryText);
        builder.part("techStack", techStack);
        if (frontendDetailsJson != null && !frontendDetailsJson.isBlank()) {
            builder.part("frontendDetails", frontendDetailsJson);
        }
        if (wireframePath != null && !wireframePath.isBlank()) {
            builder.part("wireframe", new FileSystemResource(wireframePath));
        }

        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMinutes(5))
                .block();
    }
}
