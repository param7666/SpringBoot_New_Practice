package com.param.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/students")
public class StudentController {

    @GetMapping 
    public String checkStundet(Authentication authentication) {
        return "Hello "+authentication.getName()+" wellcome to Student dashborad";
    }
}
