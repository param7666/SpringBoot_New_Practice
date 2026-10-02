package com.param.service;

import org.springframework.stereotype.Service;

import com.param.client.ProductComponant;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;


@Service 
public class UserService {

    private final ProductComponant productComponant;

    public UserService(ProductComponant productComponant) {
        this.productComponant = productComponant;
    }

    @Retry(name = "product", fallbackMethod = "getProductFallback")
    @CircuitBreaker (name = "product")
    @Bulkhead(name = "product")
    public String getProductInfo() {
        return productComponant.getProduct();
    }

    public String getProductFallback(Throwable t) {
        return "Product Service is currently unavailable. Please try again later.";
    }

}
