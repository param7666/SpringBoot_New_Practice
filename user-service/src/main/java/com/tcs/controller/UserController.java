package com.tcs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

	@GetMapping("/users")
	public String getUsers() {
		return "User service:: all users";
	}
	
	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable Integer id) {
		return "User service:: user with id"+id;
	}
}
