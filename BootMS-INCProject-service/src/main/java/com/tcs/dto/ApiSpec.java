package com.tcs.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiSpec {
    private String name;
    private String method;
    private String path;
    private String role;
}