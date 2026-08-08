package com.tcs.authservice;

import org.springframework.stereotype.Service;

@Service
	public class AuthService {

	    private final JwtService jwtService;

	    public AuthService(JwtService jwtService) {
	        this.jwtService = jwtService;
	    }

	    public String login(String username, String password) {

	        String role = null;

	        if (username.equals("john") && password.equals("1234")) {
	            role = "USER";
	        }

	        if (username.equals("admin") && password.equals("admin123")) {
	            role = "ADMIN";
	        }

	        if (role == null) {
	            throw new RuntimeException("Invalid username or password");
	        }

	        return jwtService.generateToken(username, role);
	    }
}
