package com.param.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product", url = "http://localhost:8082")
public interface ProductComponant {

    @GetMapping("/product")
    String getProduct();

}
