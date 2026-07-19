package com.tcs.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.entity.Doctor;
import com.tcs.service.IDoctorService;

@Component
public class CustomeQueryRunner implements CommandLineRunner{

	@Autowired
	private IDoctorService service;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		try {
			
//			service.showAllDoctorBySpecialization("Heart")
//			.forEach(System.out::println);
			
//			List<Doctor> doctors=service.showDoctorByIncome(80000.0);
//			if(doctors.size()==0) System.out.println("Doctors not Found...");
//			else doctors.forEach(System.out::println);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
