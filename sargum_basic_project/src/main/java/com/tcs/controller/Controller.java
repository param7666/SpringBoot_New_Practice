package com.tcs.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.User;


@RestController
@RequestMapping("/user")
public class Controller {
	
	List<User> list=new ArrayList<>();
	
	
	@GetMapping("/show")
	public String display() {
		return "Hii sargum";
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		list.add(user);
		//return "user added successfully";
		return ResponseEntity.status(201).body(user);
	}
}
