package com.tcs.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectGenerationResponse {
    private UUID projectId;
    private String status;   // "COMPLETED" or "FAILED"
    private String message;
}
