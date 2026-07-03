package com.nt.sbeans;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("wish")
public class WishMessage {

	//@Autowired
	private LocalDate date;
	
	//@Autowired
	private LocalTime time;
	
	//@Autowired
	public void setDate(LocalDate date) {
		this.date=date;
	}
	
	//@Autowired
	public void setTime(LocalTime time) {
		this.time=time;
	}
	
	public WishMessage() {
		System.out.println("WishMessage.WishMessage()");
	}
	
	@Autowired
	public WishMessage(LocalDate date, LocalTime time) {
		System.out.println("WishMessage.WishMessage()");
		this.time=time;
		this.date=date;
	}
	
	
	public void displayDateAndTime() {
		System.out.println("WishMessage.displayDateAndTime()");
		System.out.println(date);
		System.out.println(time);
	}
}
