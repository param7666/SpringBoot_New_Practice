package com.tcs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AiGeneratedSpec {
    private ProjectConfiguration projectConfiguration;
    private ApplicationSpecification applicationSpecification;
}