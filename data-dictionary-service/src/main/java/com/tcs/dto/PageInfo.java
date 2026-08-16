package com.tcs.dto;

import lombok.Data;

@Data
public class PageInfo {
    private String name;         // e.g. "LoginPage"
    private String route;        // e.g. "/login"
    private String boundEntity;  // e.g. "Project" — null if not tied to one entity
}
