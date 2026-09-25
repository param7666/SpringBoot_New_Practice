package com.param.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.param.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,Integer>{

    List<Employee> findByEmployeeName(String employeeName);
}
