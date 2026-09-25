package com.tcs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.entity.Employee;
import com.tcs.repository.EmployeeRepo;
import com.tcs.entity.IdCard;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepo repo;
	
	@Transactional
	public Employee create(Employee e) {
		return repo.save(e);
	}
	
	@Transactional(readOnly = true)
	public List<Employee> getAllEmp(){
		return repo.findAll();
	}

	@Transactional(readOnly = true)
	public Employee getEmpById(Long id) {
		return repo.findById(id)
		.orElseThrow(()-> new EntityNotFoundException("Employee not found with id: " + id));
	}

	@Transactional 
	public Employee updateIdCard(Long id,IdCard idCard) {
		Employee emp = repo.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Employee not found with id: " + id));

		if(emp.getIdCard()== null) {
			emp.setIdCard(idCard);
		}else{
			emp.getIdCard().setCardNumber(idCard.getCardNumber());
			emp.getIdCard().setIssueDate(idCard.getIssueDate());
		}

		return emp;
	}

	@Transactional
	public void deleteEmp(Long id) {
		Employee emp = repo.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("Employee not found with id: " + id));
		repo.delete(emp);
	}
	


}
