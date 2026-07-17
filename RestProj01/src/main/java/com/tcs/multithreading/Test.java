package com.tcs.multithreading;

public class Test extends Thread{
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
	
public static void main(String[] args) {
	System.out.println(Thread.currentThread().getName());
	
	Test t=new Test();
	t.setName("param");
	Thread th=new Thread(t);
	th.setName("Sundar");
	th.start();
}
}
