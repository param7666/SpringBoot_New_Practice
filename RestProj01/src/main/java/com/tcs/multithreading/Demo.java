package com.tcs.multithreading;

public class Demo implements Runnable{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
	
	public static void main(String[] args) {
		Demo d=new Demo();
		Thread t=new Thread(d);
		t.setName("param");
		t.start();
	}

}
