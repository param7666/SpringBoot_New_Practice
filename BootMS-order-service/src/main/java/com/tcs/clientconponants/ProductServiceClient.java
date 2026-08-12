package com.tcs.clientconponants;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tcs.dto.ProductResponse;

@FeignClient(name = "product-service")
public interface ProductServiceClient {

	 @GetMapping("/api/products/{id}")
	 ProductResponse getProductById(@PathVariable("id") Long id);

	 @PatchMapping("/api/products/{id}/reduce-stock")
	 ProductResponse reduceStock(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity);
}
