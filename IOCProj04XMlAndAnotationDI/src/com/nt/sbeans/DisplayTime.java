package com.nt.sbeans;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dt")
public class DisplayTime {

	//@Autowired // feild injection
	private LocalDateTime time;
	
//	@Autowired
//	public void setTime(LocalDateTime time) {
//		this.time=time;
//	}
	
//	@Autowired // constructor injection
//	public DisplayTime(LocalDateTime time) {
//		this.time=time;
//	}
	
	@Autowired // arbitory method injection
	public void methodInjection(LocalDateTime time) {
		this.time=time;
	}
	
	public void displayTime() {
		System.out.println("Display Time "+time);
	}
}
