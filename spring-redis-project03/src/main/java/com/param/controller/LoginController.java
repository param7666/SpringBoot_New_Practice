package com.param.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.param.dto.UserLoginRequest;
import com.param.entity.User;
import com.param.service.UserService;
import com.param.utils.RateLimiter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/login")
@RequiredArgsConstructor 

public class LoginController {

    private final UserService service;

    private final RateLimiter rateLimiter;

    private static final int MAX_REQUESTS = 5; // Maximum requests allowed
    private static final long WINDOW_SECONDS = 60; // Time window in seconds


    
    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserLoginRequest user, HttpServletRequest request) {
        String clientIp = getClientIp(request);
        String rateLimitKey="login_attempts:"+clientIp;

        if(!rateLimiter.isAllowed(rateLimitKey, MAX_REQUESTS, WINDOW_SECONDS)){
            return ResponseEntity.status(429).body("Too many login attempts. Please try again later.");
        }

        try {
            User logedInUser =service.login(user);
            return ResponseEntity.ok().body(logedInUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }


    private String getClientIp(HttpServletRequest req){
        
        // Check for the X-Forwarded-For header, which is used to identify the 
        // originating IP address of a client connecting to a web server through an HTTP proxy or load balancer.
       
        String forworded=req.getHeader("X-Forwarded-For"); 
        if(forworded!=null && !forworded.isEmpty()){

            //first IP if behind a proxy/load balancer
            return forworded.split(",")[0]; 
        }

        //direct client IP
        return req.getRemoteAddr(); 

    }
}
