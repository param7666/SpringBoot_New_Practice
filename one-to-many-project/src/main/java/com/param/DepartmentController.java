package com.param;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.param.entity.Department;
import com.param.entity.Employee;
import com.param.service.DepartmentService;

import lombok.AllArgsConstructor;

@RestController 
@RequestMapping ("/departments")
@AllArgsConstructor 
public class DepartmentController {
    
    private final DepartmentService departmentService;

    @PostMapping 
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable int id) {
        Department department = departmentService.findById(id);
        return ResponseEntity.ok(department);   
    }

    @PostMapping ("/{departmentId}/employees")
    public ResponseEntity<Employee> addEmployeeToDepartment(@PathVariable int departmentId, @RequestBody Employee employee) {
        Employee savedEmployee = departmentService.addEmployee(departmentId, employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);   
    }

}
