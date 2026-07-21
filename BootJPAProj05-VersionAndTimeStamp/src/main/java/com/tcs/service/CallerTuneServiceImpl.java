package com.tcs.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.entity.CallerTune;
import com.tcs.repository.CallerTuneRepo;

@Service
public class CallerTuneServiceImpl implements ICallerTuneService{

	@Autowired
	private CallerTuneRepo repo;
	
	
	@Override
	public String SaveCallerTuneInfo(CallerTune tune) throws Exception {
		if(tune!=null) {
			Integer tuneId=repo.save(tune).getTuneId();
			return "Caller tune info saved with id number "+tuneId;
		}
		else {
			throw new RuntimeException("Object can not be null...");
		}
		
	}

	@Override
	public String updateTuneInfoById(Integer id, String tuneName, String movieName) throws Exception {
		if(id<=0 ) {
			throw new RuntimeException("Id can not be less than 0");
		}
		
		if(id==null || tuneName==null || movieName==null) {
			throw new RuntimeException("Values can not be null");
		}
		
		Optional<CallerTune> callertune = repo.findById(id);
			if(callertune.isPresent()) {
				CallerTune tune= callertune.get();
				tune.setTuneName(tuneName);
				tune.setMovieName(movieName);
				repo.save(tune);
				
				return "Callertune object is updated..";
			} else {
				throw new  RuntimeException("Invalid id");
			}
	}

	@Override
	public CallerTune showCallerTuneInfoById(Integer id) throws Exception {
		
		if(id<=0 || id==null) {
			throw new RuntimeException("Id can not be less than 0");
		}
		
		Optional<CallerTune> callertune=repo.findById(id);
		if(callertune.isPresent()) return callertune.get();
		else throw new RuntimeException("Invalid id found..");
	}

	
}
