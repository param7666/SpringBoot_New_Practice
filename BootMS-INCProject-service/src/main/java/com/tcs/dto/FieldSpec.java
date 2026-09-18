package com.tcs.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldSpec {
    private String name;
    private String type;
    private boolean primaryKey;
    private boolean required;
}
