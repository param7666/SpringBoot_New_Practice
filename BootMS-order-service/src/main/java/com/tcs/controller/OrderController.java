package com.tcs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.CreateOrderRequest;
import com.tcs.dto.OrderResponse;
import com.tcs.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;
	
	@PostMapping
	public ResponseEntity<?> placeOrder(
			@RequestHeader("X-Auth-Username")String authUsername,
			@Valid @RequestBody CreateOrderRequest req) {
		
		try {
			if(authUsername==null) return new ResponseEntity<String>("Invalid username",HttpStatus.BAD_REQUEST);
			if(req==null) return new ResponseEntity<String>("Invalid Request Data",HttpStatus.BAD_REQUEST);
			OrderResponse response=service.placeOrder(authUsername, req);
			return new ResponseEntity<OrderResponse>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
