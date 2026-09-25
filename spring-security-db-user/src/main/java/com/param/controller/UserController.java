package com.param.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {

   @GetMapping 
   public String checkUserEndpoint(Authentication authentication) {
    return "Hello "+authentication.getName()+" welcome ";
   }
}
