package com.param.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.param.service.UserService;

@RestController 
@RequestMapping ("/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/product-info")
    public String getProductInfo() {
        return userService.getProductInfo();
    }
}
