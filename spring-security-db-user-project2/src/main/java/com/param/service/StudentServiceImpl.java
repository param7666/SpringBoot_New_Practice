package com.param.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.param.dtos.StudentLoginRequest;
import com.param.dtos.StudentLoginResponse;
import com.param.entity.Student;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class StudentServiceImpl {
    
    private final AuthenticationManager authenticationManager;




    public StudentLoginResponse login(StudentLoginRequest req){
        Authentication authentication =authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.username(), req.password())
        );
        Student student=(Student) authentication.getPrincipal();
        StudentLoginResponse response=new StudentLoginResponse();
        BeanUtils.copyProperties(student, response);
        return  response;
    }
}