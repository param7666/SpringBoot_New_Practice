package com.tcs.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tcs.dto.ProjectDetails;

import java.time.Duration;
import java.util.List;

@Service
public class SpringInitializrClient {

    private final WebClient webClient;

    public SpringInitializrClient(
            WebClient.Builder webClientBuilder,
            @Value("${spring-initializr.base-url:https://start.spring.io}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    /**
     * Downloads an empty starter project zip from start.spring.io as raw bytes.
     */
    public byte[] downloadEmptyProject(ProjectDetails project, List<String> dependencies) {
        String type = "MAVEN".equalsIgnoreCase(project.getBuildTool())
                ? "maven-project" : "gradle-project-spring-boot";

        String depsParam = String.join(",", dependencies);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/starter.zip")
                        .queryParam("type", type)
                        .queryParam("language", project.getLanguage() != null ? project.getLanguage().toLowerCase() : "java")
                        .queryParam("groupId", project.getGroupId())
                        .queryParam("artifactId", project.getArtifactId())
                        .queryParam("name", project.getName())
                        .queryParam("packageName", project.getPackageName())
                        .queryParam("packaging", project.getPackaging() != null ? project.getPackaging().toLowerCase() : "jar")
                        .queryParam("javaVersion", project.getJavaVersion())
                        .queryParam("dependencies", depsParam)
                        .build())
                .retrieve()
                .bodyToMono(byte[].class)
                .timeout(Duration.ofSeconds(30))
                .block();
    }
}
