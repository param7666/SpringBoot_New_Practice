package com.stackgen.metadataservice.dto; import java.util.List; public record EntityMetadataResponse(String name,String tableName,List<FieldMetadataResponse> fields){ }
