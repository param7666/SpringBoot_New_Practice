package com.param.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.param.dto.UserLoginRequest;
import com.param.entity.User;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserService {

    private final AuthenticationManager authenticationManager;
    
    public User login(UserLoginRequest req) {
        
        // Authenticate the user using the provided username and password
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        // Set the authenticated user in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Return the authenticated user details
        return (User) authentication.getPrincipal();

    }


}
