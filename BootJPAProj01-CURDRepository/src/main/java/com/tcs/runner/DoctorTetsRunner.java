package com.tcs.runner;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.entity.Doctor;
import com.tcs.service.DoctorService;

@Component
public class DoctorTetsRunner implements CommandLineRunner{
	
	@Autowired
	private DoctorService service;

	@Override
	public void run(String... args) throws Exception {
		Doctor d=new Doctor("Sundar", "BAMS",45000000L, 7666845301L,"Heart");
		
		try {
			
			//String result=service.inserDoctor(d);
			//System.out.println(result);
			
			Optional<Doctor> doc=service.getDoctorById(1L);
			System.out.println(doc);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
