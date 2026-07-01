package com.nt.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nt.sbeans.WishMessageGenerator;

public class DIMain {
public static void main(String[] args) {
	// to create beans
	ClassPathXmlApplicationContext  ctx=new ClassPathXmlApplicationContext("com/nt/cfgs/ApplicationContext.xml");
	
	// retrive bean
	Object obj=ctx.getBean("wmg");
	
	//downcasting
	WishMessageGenerator message=(WishMessageGenerator)obj;
	
	message.displayDateAndTime();
}
}
