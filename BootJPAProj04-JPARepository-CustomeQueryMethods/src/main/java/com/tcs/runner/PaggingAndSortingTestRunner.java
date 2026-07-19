package com.tcs.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.tcs.entity.Doctor;
import com.tcs.service.IDoctorService;

//@Component
public class PaggingAndSortingTestRunner implements CommandLineRunner{
	
	@Autowired
	private IDoctorService service;

	@Override
	public void run(String... args) throws Exception {
		
		try {
			
//			Iterable<Doctor> doctors=service.showAllDoctor(true, "name");
//			doctors.forEach(System.out::println);
			
			Page<Doctor> page=service.showAllDoctorByPageAndSorting(0, 2, false, "name");
			page.forEach(System.out::println);
			
			System.out.println("Requested page number: "+page.getNumber()+1);
			System.out.println("Total Pages: "+page.getTotalPages());
			System.out.println("Is it the first page: "+page.isFirst());
			System.out.println("It is the last page: "+page.isLast());
			System.out.println("Page size: "+page.getSize());
			System.out.println("Number of record in this page: "+page.getNumberOfElements());
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
