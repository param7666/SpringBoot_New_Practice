package com.tcs.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tcs.dto.StudentDTO;
import com.tcs.entity.Student;

public interface IStudentService extends UserDetailsService{

	public Student register(Student s);
	
	public List<Student> findAllStudent();
}
