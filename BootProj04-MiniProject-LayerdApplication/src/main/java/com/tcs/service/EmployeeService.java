package com.tcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.DAO.IEmployeeDAO;
import com.tcs.model.Employee;

@Service
public class EmployeeService implements IEmpService {

	@Autowired
	private IEmployeeDAO dao;

	@Override
	public List<Employee> getEmpByDesg(String d1, String d2, String d3) throws Exception {
		//d1=d1.toUpperCase();
		//d2=d2.toUpperCase();
		//d3=d3.toUpperCase();
		
		List<Employee> empList=dao.getEmployeeByDesg(d1, d2, d3);
		empList.forEach(emp->{
			emp.setGrossSalary(emp.getSalary()+(emp.getSalary()*0.4));
			emp.setNetSalary(emp.getGrossSalary()-(emp.getGrossSalary()*0.15));
		});
		return empList;
	}

	@Override
	public String registerEmp(Employee e) throws Exception {
		int result=dao.insertEmployee(e);
		return result==0?"Emp not Registerd":"Emp Registration Successfull";
	}
	
	
}
