package com.tcs.service;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.tcs.dto.WireframeInfo;

@Service

public class WireframeService {

	private final WebClient webClient;
	
	public WireframeService(WebClient.Builder webClientBulder,
			@Value("${vision.api.base-url}") String visionApiBaseUrl) {
		
		this.webClient=webClientBulder.baseUrl(visionApiBaseUrl).build();
	}
	
	public WireframeInfo describeWireframe(MultipartFile wireframeFile) throws Exception {
        String base64Image = Base64.getEncoder().encodeToString(wireframeFile.getBytes());
 
        String prompt = """
                Describe this UI wireframe as plain text. For each screen, list:
                - screen name
                - visible fields/inputs
                - buttons/actions
                Keep it concise and structured, no extra commentary.
                """;
 
        Map<String, Object> requestBody = Map.of(
                "prompt", prompt,
                "image_base64", base64Image
        );
 
        Map<String, Object> response = webClient.post()
                .uri("/describe")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
 
        String description = response != null ? String.valueOf(response.get("description")) : "";
        return new WireframeInfo(description);
    }
}
