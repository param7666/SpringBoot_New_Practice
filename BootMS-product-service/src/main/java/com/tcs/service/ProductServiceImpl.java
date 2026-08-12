package com.tcs.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tcs.dto.CreateProductRequest;
import com.tcs.dto.ProductResponse;
import com.tcs.dto.UpdateProductRequest;
import com.tcs.entity.Product;
import com.tcs.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{
	
	private final ProductRepository repo;

	@Override
	public ProductResponse createProduct(CreateProductRequest request) throws Exception {
		Product product=new Product();
		BeanUtils.copyProperties(request, product);
		Product saved= repo.save(product);
		return toResponse(saved);
	}

	@Override
	public ProductResponse getProductById(Long id) throws Exception {
		
		Product product=repo.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id"));
		
		return toResponse(product);
	}

	@Override
	public List<ProductResponse> getAllProducts() throws Exception {
		// get the product list
		List<Product> products=repo.findAll();
		// convert into response
		List<ProductResponse> ps=products.stream().map(this::toResponse).toList();
		//return response
		return ps;
	}

	@Override
	public ProductResponse updateProduct(Long id, UpdateProductRequest request) throws Exception {
		// get the product if not found throw the exception
		Product product=repo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid id"));
		
		//copy null properties
		copyNonNullProperties(request, product);
		
		// resave the product
		Product updated=repo.save(product);
		
		// convert to response and return
		return toResponse(updated);
	}

	@Override
	public String deleteProduct(Long id) throws Exception {
		// get the product if not found throw the exception
		Product product=repo.findById(id).
				orElseThrow(()->new IllegalArgumentException("Invalid id"));
				
		repo.delete(product);
		return "Product with id number "+product.getId()+" is Deleted.";
	}

	@Override
	public ProductResponse reduceStock(Long id, Integer quantity) throws Exception {
		Product product=repo.findById(id).
				orElseThrow(()->new IllegalArgumentException("Invalid id"));
		// reduce the stock
		product.setStock(product.getStock()-quantity);
		// resave the product object
		Product updated=repo.save(product);
		// convert and return 
		return toResponse(updated);
	}
	
	private ProductResponse toResponse(Product product) {
		ProductResponse ps=new ProductResponse();
		BeanUtils.copyProperties(product, ps);
		return ps;
	}

	private void copyNonNullProperties(UpdateProductRequest request, Product product) {
        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getStock() != null) product.setStock(request.getStock());
        if (request.getCategory() != null) product.setCategory(request.getCategory());
    }
	
}
