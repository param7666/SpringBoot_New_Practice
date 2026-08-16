package com.tcs.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * Forwards the raw, unprocessed pieces to Django AI Service — the parsed
 * dictionary text, the wireframe file itself (not a description), tech
 * stack, and structured frontend form details as JSON text. Django owns
 * all prompt-building, vision handling, and LLM calls; this client's only
 * job is faithful forwarding and returning whatever comes back.
 *
 * Called from a background (@Async) thread, so a long-running blocking
 * call here does NOT block any user-facing HTTP request.
 */
@Service
public class DjangoAiClient {

    private final WebClient webClient;

    public DjangoAiClient(WebClient.Builder webClientBuilder,
                           @Value("${django-ai-service.base-url:http://django-ai-service}") String baseUrl) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * @param projectId          caller-supplied or generated project identifier
     * @param dictionaryText     raw multi-sheet dictionary dump, as-is
     * @param techStack          e.g. "SPRING_BOOT"
     * @param frontendDetailsJson JSON string of the frontend form (pages/components/events), or null
     * @param wireframePath      absolute path to the wireframe file on disk, or null if none was uploaded
     * @return raw JSON string returned by Django (the structured project spec)
     */
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
            FileSystemResource wireframeResource = new FileSystemResource(wireframePath);
            builder.part("wireframe", wireframeResource);
        }

        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(org.springframework.web.reactive.function.BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                // Local LLM inference (possibly two calls: vision + extraction) can be slow.
                // This runs on a background thread, so a generous timeout here is safe.
                .timeout(Duration.ofMinutes(5))
                .block();
    }
}