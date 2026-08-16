package com.tcs.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Incoming shape for the "tell us more about your frontend" form.
 * Sent as a JSON part within the multipart/form-data request
 * (part name: "frontendDetails", Content-Type: application/json).
 */
@Data
public class FrontendFormRequest {
    private List<PageInfo> pages = new ArrayList<>();
    private List<ComponentInfo> components = new ArrayList<>();
    private List<EventInfo> events = new ArrayList<>();
    private String extraNotes;
}
