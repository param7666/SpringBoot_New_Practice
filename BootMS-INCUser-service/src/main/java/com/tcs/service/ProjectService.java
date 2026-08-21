package com.tcs.service;

import com.tcs.dto.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectService {

  
    public List<ProjectDto> getProjectsByUserId(Long userId) {
        // Placeholder until ProjectRepository exists
        return Collections.emptyList();
    }
}