package com.tcs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import com.tcs.entity.Employee;
import com.tcs.service.EmployeeService;
import com.tcs.entity.IdCard;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
@RequestMapping("/employees")
@RequiredArgsConstructor 
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("test")
    public String test() {
        return "Hello, World!";
    }
    

    @PostMapping 
    public ResponseEntity<?> create( @RequestBody Employee emp) {
        try{
            return ResponseEntity.ok(employeeService.create(emp));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping 
    public ResponseEntity<?> getAllEmp() {
        try{
            return ResponseEntity.ok(employeeService.getAllEmp());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpById(Long id) {
        try{
            return ResponseEntity.ok(employeeService.getEmpById(id));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping 
    public ResponseEntity<?> deleteEmp(Long id) {
        try{
            employeeService.deleteEmp(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping 
    public ResponseEntity<?> updateIdCard(Long id, IdCard idCard) {
        try{
            return ResponseEntity.ok(employeeService.updateIdCard(id, idCard));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
