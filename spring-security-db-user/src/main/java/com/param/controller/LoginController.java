package com.param.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.param.dtos.LoginRequest;
import com.param.entity.User;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/login")
@RequiredArgsConstructor 
public class LoginController {

    private final AuthenticationManager authenticationManager;


    @PostMapping 
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        
        User user = (User) authentication.getPrincipal();
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }


}
