package com.tcs;


import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tcs.controller.EmpController;
import com.tcs.model.Employee;

@SpringBootApplication
public class BootProj04MiniProjectLayerdApplication2 {

	public static void main(String[] args) {
		ApplicationContext ctx=
				SpringApplication.run(BootProj04MiniProjectLayerdApplication2.class);
		
		EmpController controller=ctx.getBean(EmpController.class);
		
		String d1="HR";
		String d2="Analyst";
		String d3="Developer";
		
		try {
			List<Employee> list=controller.getEmpByDesg(d1, d2, d3);
			list.forEach(e->System.out.println(e));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
