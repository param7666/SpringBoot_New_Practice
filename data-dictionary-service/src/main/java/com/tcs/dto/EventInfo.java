package com.tcs.dto;


import lombok.Data;

@Data
public class EventInfo {
    private String trigger;    // e.g. "onClick", "onSubmit"
    private String component;  // which component this event is on, e.g. "LoginForm"
    private String action;     // plain description, e.g. "calls Login API" or "navigates to /projects"
}
