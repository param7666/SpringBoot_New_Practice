package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tcs.comp.Student;

@SpringBootApplication
public class DootProj02DiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx= SpringApplication.run(DootProj02DiApplication.class, args);
		
		Student std=ctx.getBean(Student.class);
		
		std.doStudy();
	}

}
