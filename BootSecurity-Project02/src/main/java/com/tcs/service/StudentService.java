package com.tcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.entity.Student;
import com.tcs.repo.StudentRepo;

@Service
public class StudentService implements IStudentService{
	
	@Autowired
	private StudentRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByEmail(username).orElseThrow(()->  new UsernameNotFoundException("user not found"));
	}

	@Override
	public Student register(Student s) {
		Student std=repo.save(s);
		return std;
	}

	@Override
	public List<Student> findAllStudent() {
		return repo.findAll();
	}

}
