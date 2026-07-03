package com.nt.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.nt.config.AppConfig;
import com.nt.sbeans.WishMessage;

public class Main {
public static void main(String[] args) {
	AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class);
	
	WishMessage msg=(WishMessage)ctx.getBean("wish");
	
	msg.displayDateAndTime();
}
}
