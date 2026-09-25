package com.param.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.param.entity.Department;
import com.param.entity.Employee;
import com.param.repository.DepartmentRepo;
import com.param.repository.EmployeeRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class DepartmentService {
    
    private final DepartmentRepo departmentRepo;

    private final EmployeeRepo employeeRepo;


    @Transactional 
    public Department saveDepartment(Department de) {
        return departmentRepo.save(de);
    }

    @Transactional (readOnly = true)
    public Department findById(int id) {
        return departmentRepo.findById(id)
        .orElseThrow(()-> new RuntimeException("Department not found with id: " + id));
    }


    @Transactional (readOnly = true)
    public List<Department> findAllDepartments() {
        return departmentRepo.findAll();
    }

    @Transactional 
    public Employee addEmployee(int departmentId,Employee employee) {
        Department department = findById(departmentId);
        department.addEmployee(employee);
        // return employeeRepo.save(employee);
        return employee;
    }


    @Transactional 
    public Employee getEmpById(Integer empId) {
        return employeeRepo.findById(empId)
        .orElseThrow(()-> new RuntimeException("Employee not found with id: " + empId));
    }

    @Transactional 
    public void removeEmployee(int departmentId, int employeeId) {
        Department department = findById(departmentId);
        Employee employee = getEmpById(employeeId);
        department.removeEmployee(employee);
        employeeRepo.delete(employee);
    }

}