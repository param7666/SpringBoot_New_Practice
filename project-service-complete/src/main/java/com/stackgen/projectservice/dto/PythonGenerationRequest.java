package com.stackgen.projectservice.dto;

import com.stackgen.projectservice.entity.GenerationType;
import java.util.UUID;

public record PythonGenerationRequest(
        UUID projectId,
        UUID userId,
        GenerationType generationType,
        StackInfo frontend,
        StackInfo backend,
        Object dataDictionary,
        Object frontendJson
) {
    public record StackInfo(String name, String language, String version) {}
}
