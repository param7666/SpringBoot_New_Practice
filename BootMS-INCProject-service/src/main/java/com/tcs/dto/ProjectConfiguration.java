package com.tcs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectConfiguration {
    private String generator;
    private ProjectDetails project;
    private List<String> dependencies = new ArrayList<>();
}
