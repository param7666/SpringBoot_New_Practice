package com.nt.sbeans;

import java.time.LocalDateTime;

public class WishMessageGenerator {

	public LocalDateTime lt;
	
	public WishMessageGenerator() {
		System.out.println("WishMessageGenerator.WishMessageGenerator()");
	}
	
	public void setLt(LocalDateTime lt) {
		this.lt=lt;
	}
	
	public void displayDateAndTime() {
		System.out.println("Current Date and time is "+lt);
	}
}
