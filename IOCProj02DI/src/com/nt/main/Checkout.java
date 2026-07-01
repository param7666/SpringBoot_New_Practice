package com.nt.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nt.sbeans.OrderService;

public class Checkout {
public static void main(String[] args) {
	ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("com/nt/cfgs/ApplicationContext.xml");
	
	Object obj=ctx.getBean("os");
	
	OrderService service=(OrderService)obj;
	
	System.out.println("Your total Bill is:: "+service.totalBill());
}
}
