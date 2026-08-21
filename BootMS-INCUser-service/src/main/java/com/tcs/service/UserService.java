package com.tcs.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tcs.dto.ChangePasswordRequest;
import com.tcs.dto.ForgotPasswordRequest;
import com.tcs.dto.ProjectDto;
import com.tcs.dto.UserResponse;
import com.tcs.entity.User;
import com.tcs.repository.UserRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectService projectService;
    private final TokenBlacklistService tokenBlacklistService;

    private static final String TEMP_PASSWORD_CHARS =
            "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ProjectService projectService,
            TokenBlacklistService tokenBlacklistService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectService = projectService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public UserResponse getCurrentUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDto(user);
    }

    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

//    public String forgotPassword(ForgotPasswordRequest request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("No account found with this email"));
//
//        String tempPassword = generateTempPassword();
//        user.setPassword(passwordEncoder.encode(tempPassword));
//        userRepository.save(user);
//
//        // TODO: send tempPassword via email instead of returning it directly once a mail service is added.
//        return tempPassword;
//    }

    public List<ProjectDto> getMyProjects(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return projectService.getProjectsByUserId(user.getId());
    }

    public void logout(String token) {
        tokenBlacklistService.blacklist(token);
    }

    private String generateTempPassword() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(TEMP_PASSWORD_CHARS.charAt(RANDOM.nextInt(TEMP_PASSWORD_CHARS.length())));
        }
        return sb.toString();
    }

    private UserResponse toDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isActive()
        );
    }
}