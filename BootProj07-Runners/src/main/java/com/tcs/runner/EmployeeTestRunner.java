package com.tcs.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.controller.EmpController;
import com.tcs.model.Employee;

@Component
public class EmployeeTestRunner implements CommandLineRunner {

	@Autowired
	private EmpController controller;
	
	@Override
	public void run(String... args) throws Exception {


		Employee e11 = new Employee();
		e11.setId(112);
		e11.setName("Sundar");
		e11.setDesg("SAP DEV");
		e11.setSalary(77000.00);
		e11.setDeptno(22);
		
		try {
			String result=controller.registerEmp(e11);
			System.out.println(result);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
