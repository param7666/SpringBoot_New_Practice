package com.tcs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tcs.model.Employee;
import com.tcs.service.IEmpService;

@Controller("empController")
public class EmpController {

	@Autowired
	private IEmpService service;
	
	public List<Employee> getEmpByDesg(String d1,String d2,String d3) throws Exception {
		return service.getEmpByDesg(d1, d2, d3);
	}
	
	public String registerEmp(Employee e) throws Exception {
		return service.registerEmp(e);
	}
}
