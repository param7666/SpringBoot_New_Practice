//package com.tcs.service;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Service
//public class DjangoAiClient {
//
//	private final WebClient webClient;
//	
//	public DjangoAiClient(WebClient.Builder webClientBuilder,
//            @Value("${django-ai-service.base-url:http://django-ai-service}") String baseUrl) {
//		this.webClient = webClientBuilder.baseUrl(baseUrl).build();
//	}
//	
//	public String sendForGeneration(String projectId, String promptText) {
//        Map<String, Object> requestBody = Map.of(
//                "project_id", projectId,
//                "prompt_text", promptText
//        );
// 
//        Map<String, Object> response = webClient.post()
//                .uri("/api/generate")
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(Map.class)
//                .block();
// 
//        return response != null ? String.valueOf(response.get("generated_code")) : "";
//    }
//}
