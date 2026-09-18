package com.tcs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerationRules {
    private boolean generateEntity;
    private boolean generateRepository;
    private boolean generateService;
    private boolean generateController;
    private boolean generateDto;
    private boolean generateBusinessLogic;
}
