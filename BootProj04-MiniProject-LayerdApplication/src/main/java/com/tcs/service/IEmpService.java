package com.tcs.service;

import java.util.List;

import com.tcs.model.Employee;

public interface IEmpService {

	public List<Employee> getEmpByDesg(String d1,String d2,String d3) throws Exception;
	
	public String registerEmp(Employee e) throws Exception;
}
