package com.tcs.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.tcs.dto.ChangePasswordRequest;
import com.tcs.dto.ForgotPasswordRequest;
import com.tcs.dto.MessageResponse;
import com.tcs.dto.ProjectDto;
import com.tcs.dto.UserResponse;
import com.tcs.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(
                userService.getCurrentUserProfile(authentication.getName())
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(authentication.getName(), request);
        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }

//    @PostMapping("/forgot-password")
//    public ResponseEntity<MessageResponse> forgotPassword(
//            @Valid @RequestBody ForgotPasswordRequest request) {
//        String tempPassword = userService.forgotPassword(request);
//        // NOTE: returned directly only because no mail service exists yet.
//        // Replace with a generic "check your email" message once email sending is wired up.
//        return ResponseEntity.ok(
//                new MessageResponse("Temporary password: " + tempPassword)
//        );
//    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> getMyProjects(Authentication authentication) {
        return ResponseEntity.ok(
                userService.getMyProjects(authentication.getName())
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ")
                ? authHeader.substring(7)
                : authHeader;
        userService.logout(token);
        return ResponseEntity.ok(new MessageResponse("Logged out successfully"));
    }
}
