package com.tcs.service;

import org.springframework.stereotype.Service;
import com.tcs.dto.ProjectDto;
import com.tcs.dto.UserResponse;
import com.tcs.entity.User;
import com.tcs.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectService projectService;

    public AdminService(UserRepository userRepository, ProjectService projectService) {
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true);
        return toResponse(userRepository.save(user));
    }

    public UserResponse deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(false);
        return toResponse(userRepository.save(user));
    }

    public List<ProjectDto> getProjectsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        return projectService.getProjectsByUserId(userId);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isActive()
        );
    }
}
