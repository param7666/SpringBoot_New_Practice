package com.tcs.service;

import com.tcs.entity.CallerTune;

public interface ICallerTuneService {

	public String SaveCallerTuneInfo(CallerTune tune) throws Exception;
	
	public String updateTuneInfoById(Integer id, String tuneName,String movieName) throws Exception;
	
	public CallerTune showCallerTuneInfoById(Integer id) throws Exception;
}
