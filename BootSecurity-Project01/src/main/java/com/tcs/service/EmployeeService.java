package com.tcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.repository.EmployeeRepo;

@Service
public class EmployeeService implements IEmployeeService{

	@Autowired
	private EmployeeRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return repo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
	}

}
