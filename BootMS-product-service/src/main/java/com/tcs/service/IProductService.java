package com.tcs.service;

import java.util.List;

import com.tcs.dto.CreateProductRequest;
import com.tcs.dto.ProductResponse;
import com.tcs.dto.UpdateProductRequest;

public interface IProductService {

	public ProductResponse createProduct(CreateProductRequest request) throws Exception;
	
	public ProductResponse getProductById(Long id) throws Exception;

	public List<ProductResponse> getAllProducts() throws Exception;

	public ProductResponse updateProduct(Long id, UpdateProductRequest request) throws Exception;
	
	public String deleteProduct(Long id) throws Exception;
	
	public ProductResponse reduceStock(Long id, Integer quantity) throws Exception;
	
}
