package com.tcs.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Insert
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Fetch all
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
}
