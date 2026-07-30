package com.tcs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcs.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	@Query("from Employee where email=?1")
	public Optional<Employee> findByEmail(String email);
}
