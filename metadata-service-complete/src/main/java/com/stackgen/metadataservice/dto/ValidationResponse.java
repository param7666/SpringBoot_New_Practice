package com.stackgen.metadataservice.dto; import java.util.List; public record ValidationResponse(boolean valid,List<ValidationIssue> issues){ }
