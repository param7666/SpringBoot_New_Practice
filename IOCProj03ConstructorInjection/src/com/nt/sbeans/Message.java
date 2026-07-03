package com.nt.sbeans;

import java.time.LocalDateTime;

public class Message {

	private LocalDateTime time;
	
	public Message(LocalDateTime time) {
		this.time=time;
	}
	
	
	public void displayTime() {
		System.out.println("Date and Time "+time);
	}
}
