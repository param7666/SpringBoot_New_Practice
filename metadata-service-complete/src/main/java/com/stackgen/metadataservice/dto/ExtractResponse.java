package com.stackgen.metadataservice.dto; import java.util.*; public record ExtractResponse(UUID projectId,int entityCount,int fieldCount,int relationshipCount,int endpointCount,String message){ }
