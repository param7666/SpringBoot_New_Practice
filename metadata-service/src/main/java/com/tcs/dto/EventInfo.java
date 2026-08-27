package com.tcs.dto;


import lombok.Data;

@Data
public class EventInfo {
    private String trigger;
    private String component;
    private String action;
}
