package com.tcs.sbeans;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Greet {

	@Autowired
	private LocalDateTime time;
	
	public void displayGreet(String name) {
		System.out.println("Welcome "+name);
		System.out.println(time);
	}
}
