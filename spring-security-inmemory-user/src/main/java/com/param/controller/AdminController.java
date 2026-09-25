package com.param.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping ("/admin")
public class AdminController {

    @GetMapping("/check-admin")
    public String checkAdmin(Authentication authentication) {
        return "Hello Admin "+authentication.getName()+" you are authorized to access this endpoint";
    }

}
