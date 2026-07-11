package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tcs.controller.EmpController;
import com.tcs.model.Employee;

@SpringBootApplication
public class BootProj04MiniProjectLayerdApplication {


	public static void main(String[] args) {
		SpringApplication.run(BootProj04MiniProjectLayerdApplication.class, args);
		
////		EmpController controller=ctx.getBean(EmpController.class);
//		
////		Employee e1=new Employee();
////		e1.setId(101);
////		e1.setName("Param");
////		e1.setDesg("Developer");
////		e1.setSalary(15000.00);
////		e1.setDeptno(10);
//		
//		Employee e2 = new Employee();
//		e2.setId(102);
//		e2.setName("Rahul");
//		e2.setDesg("Tester");
//		e2.setSalary(18000.00);
//		e2.setDeptno(20);
//
//		Employee e3 = new Employee();
//		e3.setId(103);
//		e3.setName("Priya");
//		e3.setDesg("Developer");
//		e3.setSalary(25000.00);
//		e3.setDeptno(10);
//
//		Employee e4 = new Employee();
//		e4.setId(104);
//		e4.setName("Amit");
//		e4.setDesg("Manager");
//		e4.setSalary(45000.00);
//		e4.setDeptno(30);
//
//		Employee e5 = new Employee();
//		e5.setId(105);
//		e5.setName("Sneha");
//		e5.setDesg("Analyst");
//		e5.setSalary(30000.00);
//		e5.setDeptno(20);
//
//		Employee e6 = new Employee();
//		e6.setId(106);
//		e6.setName("Kiran");
//		e6.setDesg("Developer");
//		e6.setSalary(28000.00);
//		e6.setDeptno(10);
//
//		Employee e7 = new Employee();
//		e7.setId(107);
//		e7.setName("Neha");
//		e7.setDesg("HR");
//		e7.setSalary(22000.00);
//		e7.setDeptno(40);
//
//		Employee e8 = new Employee();
//		e8.setId(108);
//		e8.setName("Rohit");
//		e8.setDesg("Tester");
//		e8.setSalary(21000.00);
//		e8.setDeptno(20);
//
//		Employee e9 = new Employee();
//		e9.setId(109);
//		e9.setName("Anjali");
//		e9.setDesg("Manager");
//		e9.setSalary(50000.00);
//		e9.setDeptno(30);
//
//		Employee e10 = new Employee();
//		e10.setId(110);
//		e10.setName("Vikram");
//		e10.setDesg("Developer");
//		e10.setSalary(32000.00);
//		e10.setDeptno(10);
//
//		Employee e11 = new Employee();
//		e11.setId(111);
//		e11.setName("Pooja");
//		e11.setDesg("Analyst");
//		e11.setSalary(27000.00);
//		e11.setDeptno(20);
//		
//		try {
//			String result=controller.registerEmp(e11);
//			System.out.println(result);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}

}
