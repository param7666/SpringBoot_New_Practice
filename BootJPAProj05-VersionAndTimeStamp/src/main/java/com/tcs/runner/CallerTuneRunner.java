package com.tcs.runner;

import com.tcs.repository.CallerTuneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.entity.CallerTune;
import com.tcs.service.ICallerTuneService;

@Component
public class CallerTuneRunner implements CommandLineRunner{

	@Autowired
	private ICallerTuneService service;


	@Override
	public void run(String... args) throws Exception {
		
		CallerTune c1 = new CallerTune("Kesariya", "Brahmastra");
//		CallerTune c2 = new CallerTune("Tum Hi Ho", "Aashiqui 2");
//		CallerTune c3 = new CallerTune("Apna Bana Le", "Bhediya");
//		CallerTune c4 = new CallerTune("Malang Sajna", "Malang Sajna");
//		CallerTune c5 = new CallerTune("Heeriye", "Heeriye");
//		CallerTune c6=null;
		
		try {
			
			//System.out.println(service.SaveCallerTuneInfo(c1));
			System.out.println(service.updateTuneInfoById(52, "Apna Bana Le", "Bhediya"));
			System.out.println(service.showCallerTuneInfoById(52));
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}
