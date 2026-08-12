package com.tcs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.CreateProductRequest;
import com.tcs.dto.ProductResponse;
import com.tcs.dto.UpdateProductRequest;
import com.tcs.service.IProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

	private final IProductService service;
	
	@PostMapping
	public ResponseEntity<?> saveProduct(@Valid @RequestBody CreateProductRequest req) {
		try {
			ProductResponse res=service.createProduct(req);
			return new ResponseEntity<ProductResponse>(res,HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Long id) {
		try {
			if(id==null) {
				return new ResponseEntity<String>("Id is null",HttpStatus.BAD_REQUEST);
			}
			ProductResponse res=service.getProductById(id);
			return new ResponseEntity<ProductResponse>(res,HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
	
	@GetMapping
	public ResponseEntity<?> getAllProducts() {
		try {
			List<ProductResponse> products=service.getAllProducts();
			if(products==null) {
				return new ResponseEntity<String>("Product not available",HttpStatus.OK);
			}
			return new ResponseEntity<List<ProductResponse>>(products,HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody UpdateProductRequest req) {
		try {
			if(id==null) {
				return new ResponseEntity<String>("Id is null",HttpStatus.BAD_REQUEST);
			}
			
			ProductResponse res=service.updateProduct(id, req);
			return new ResponseEntity<ProductResponse>(res,HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
		try {
			
			if(id==null) {
				return new ResponseEntity<String>("Id is null",HttpStatus.BAD_REQUEST);
			}
			
			String result=service.deleteProduct(id);
			return new ResponseEntity<String>(result,HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/{id}/reduce-stock")
	public ResponseEntity<?> reduceStock(
	        @PathVariable Long id,
	        @RequestParam Integer quantity) {
		try {
			ProductResponse res=service.reduceStock(id, quantity);
			return new ResponseEntity<ProductResponse>(res,HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
