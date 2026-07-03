package com.nt.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nt.sbeans.Message;

public class ConstrutorInjectionMain {
public static void main(String[] args) {
	ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("com/nt/cfgs/ApplicationContext.xml");
	
	Message message=(Message)ctx.getBean("msg");
	message.displayTime();
}
}
