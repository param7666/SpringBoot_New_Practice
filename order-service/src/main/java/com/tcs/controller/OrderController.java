package com.tcs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

	
	@GetMapping("/orders")
	public String getAllOrders() {
		return "Order service:: get all orders";
	}
	
	@GetMapping("/orders/{id}")
	public String getOrderById(@PathVariable Integer id) {
		return "Order service:: order with id "+id;
	}
}
