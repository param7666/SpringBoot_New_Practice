package com.tcs.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{

	public Optional<Student> findByEmail(String email);
}
