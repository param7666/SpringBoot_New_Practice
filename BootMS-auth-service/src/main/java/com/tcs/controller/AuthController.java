package com.tcs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.DTO.AuthResponse;
import com.tcs.DTO.LoginRequest;
import com.tcs.DTO.RegisterRequest;
import com.tcs.service.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	
	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
		AuthResponse response=authService.register(req);
		return new ResponseEntity<AuthResponse>(response,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest login) {
		AuthResponse response=authService.login(login);
		return new ResponseEntity<AuthResponse>(response,HttpStatus.OK);
	}
}
