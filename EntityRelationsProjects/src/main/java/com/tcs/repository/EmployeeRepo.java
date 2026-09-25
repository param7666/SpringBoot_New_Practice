package com.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
