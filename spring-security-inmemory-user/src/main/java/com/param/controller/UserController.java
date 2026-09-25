package com.param.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@PreAuthorize ("hasRole('USER')")
@RequestMapping ("/user")
public class UserController {

    @GetMapping ("/check")
    public String checkUser(Authentication authentication){
        return "Hello User "+authentication.getName()+" you are authorized to access this endpoint";
    }
}
