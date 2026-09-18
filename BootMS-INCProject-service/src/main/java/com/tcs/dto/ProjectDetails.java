package com.tcs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetails {
    private String name;
    private String groupId;
    private String artifactId;
    private String packageName;
    private String buildTool;
    private String language;
    private String javaVersion;
    private String packaging;
}
