package com.stackgen.metadataservice.dto; import java.util.*; public record ReadResponse(List<String> headers,List<Map<String,String>> rows,int rowCount){ }
