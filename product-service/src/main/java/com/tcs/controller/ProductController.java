package com.tcs.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

	  @Value("${server.port}")
	   private String port;
	  
	@GetMapping("/products")
	public String getAllProducts() {
		return "Product Services "+port;
	}
	
	@GetMapping("/products/{id}")
	public String getProductById(@PathVariable Integer id) {
		return "Product services:: product with id"+id;
	}
}
