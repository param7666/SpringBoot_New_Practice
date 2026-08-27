package com.tcs.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ComponentInfo {
    private String name;
    private String type; // FORM, TABLE, BUTTON, NAV
    private List<String> fields = new ArrayList<>();
}