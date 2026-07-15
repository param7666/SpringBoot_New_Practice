package com.tcs.runner;

import java.util.List;
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
		
		Doctor d1 = new Doctor("Dr. Amit Sharma", "MBBS", 1200000L, 9876543210L, "Cardiology");
		Doctor d2 = new Doctor("Dr. Priya Singh", "MD", 1500000L, 9876543211L, "Neurology");
		Doctor d3 = new Doctor("Dr. Rahul Verma", "MS", 1000000L, 9876543212L, "Orthopedics");
		Doctor d4 = new Doctor("Dr. Sneha Patil", "MBBS", 900000L, 9876543213L, "Pediatrics");
		Doctor d5 = new Doctor("Dr. Vikas Rao", "DM", 1800000L, 9876543214L, "Gastroenterology");
		
		Iterable<Doctor> doctors=List.of(d1,d2,d3,d4,d5);
		
		try {
			
			//String result=service.inserDoctor(d); // inserting data
			//System.out.println(result);
			
//			Optional<Doctor> doc=service.getDoctorById(1L); // fetching data based on id
//			System.out.println(doc);

			
//			Iterable<Doctor> it=service.showAllDoctor();
//			it.forEach(System.out::println);
			
//			String result=service.RegisterAllDoctor(doctors);
//			System.out.println(result);
//			
			
			String docExits=service.checkDoctorExit(53L);
			System.out.println(docExits);
			
			Long count=service.getAllDoctorCount();
			System.out.println("Total Count of the doctor:: "+count);
			
			String delete=service.deleteDoctorById(55L);
			System.out.println(delete);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
