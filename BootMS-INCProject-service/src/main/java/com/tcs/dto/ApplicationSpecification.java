package com.tcs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationSpecification {
    private String database;
    private List<EntitySpec> entities = new ArrayList<>();
    private List<ApiSpec> apis = new ArrayList<>();
    private GenerationRules generationRules;
}
