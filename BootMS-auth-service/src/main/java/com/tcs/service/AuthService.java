package com.tcs.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcs.DTO.AuthResponse;
import com.tcs.DTO.LoginRequest;
import com.tcs.DTO.RegisterRequest;
import com.tcs.config.JwtUtil;
import com.tcs.entity.User;
import com.tcs.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final UserRepository repo;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authmanager;
	private final JwtUtil jwtUtil;
	
	public AuthResponse register(RegisterRequest req) {
		if(repo.existsByUsername(req.getUsername())) {
			throw new IllegalArgumentException("Username allready exists");
		}
		
		if(repo.existsByEmail(req.getEmail())) {
			throw new IllegalArgumentException("Email allready exists");
		}
		
		User user = User.builder()
				.username(req.getUsername())
				.password(encoder.encode(req.getPassword()))   // ✅ encode before saving
				.email(req.getEmail())
				.createdAt(LocalDateTime.now())
				.build();
		
		repo.save(user);
		
		 String token = jwtUtil.generateToken(user.getUsername());

	        return AuthResponse.builder()
	                .token(token)
	                .tokenType("Bearer")
	                .username(user.getUsername())
	                .email(user.getEmail())
	                .build();
	}
	
	
	
	public AuthResponse login(LoginRequest request) {

		authmanager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = repo.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
