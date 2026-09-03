package com.stackgen.projectservice.dto;

public record PythonGenerationResponse(
        String status,
        String zipFileKey,
        String zipFileName
) {}
