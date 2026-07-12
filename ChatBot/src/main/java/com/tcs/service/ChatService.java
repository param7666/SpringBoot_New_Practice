package com.tcs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcs.dto.OpenAiRequest;
import com.tcs.dto.OpenAiResponse;

import lombok.RequiredArgsConstructor;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final RestTemplate template;
	
    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;
    
    
    public String getChatResponse(String userMessage) {
    	// 1 build the request body 
    	OpenAiRequest.Message message= new OpenAiRequest.Message("user",userMessage);
    	OpenAiRequest requestBody= new OpenAiRequest(model, List.of(message));
    	
    	// 2. Build headers (OpenAI requires the API key as a Bearer token)
    	
    	HttpHeaders  headers=new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(apiKey);
    	
    	 // 3. Wrap body + headers into one HttpEntity
    	
    	HttpEntity<OpenAiRequest> entity=new HttpEntity<>(requestBody,headers);
    	
    	// 4. Make the POST call
    	OpenAiResponse res=template.postForObject(apiUrl, entity, OpenAiResponse.class);
    	
    	if(res!=null && !res.getChoices().isEmpty()) {
    		return res.getChoices().get(0).getMessage().getContent();
    	}
    	return "No response from model";
    }
}
