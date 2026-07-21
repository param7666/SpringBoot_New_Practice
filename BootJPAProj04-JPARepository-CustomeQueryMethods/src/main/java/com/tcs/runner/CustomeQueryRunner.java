package com.tcs.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.entity.Doctor;
import com.tcs.repository.DoctorRepo;
import com.tcs.service.IDoctorService;

@Component
public class CustomeQueryRunner implements CommandLineRunner{

	@Autowired
	private IDoctorService service;
	
	//direct implemetion of repo
	
	@Autowired
	private DoctorRepo repo;
	
	@Override
	public void run(String... args) throws Exception {
		
		
		try {
			
//			service.showAllDoctorBySpecialization("Heart")
//			.forEach(System.out::println);
			
//			List<Doctor> doctors=service.showDoctorByIncome(80000.0);
//			if(doctors.size()==0) System.out.println("Doctors not Found...");
//			else doctors.forEach(System.out::println);
			
			List<Object[]> doctors=repo.findDoctorBetweenSalary(40000.0, 1500000.0);
			doctors.forEach(a->{
				for(Object ar:a) {
					System.out.println(ar);
				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
