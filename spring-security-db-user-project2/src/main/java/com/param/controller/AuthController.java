package com.param.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.param.dtos.StudentLoginRequest;
import com.param.service.StudentServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/login")
public class AuthController {

    private final StudentServiceImpl service;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody StudentLoginRequest request) {
        try {
            return ResponseEntity.status(200).body(service.login(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }


}
