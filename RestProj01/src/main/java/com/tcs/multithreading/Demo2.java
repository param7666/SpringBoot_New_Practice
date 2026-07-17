package com.tcs.multithreading;

public class Demo2 implements Runnable {

	@Override
	public void run() {
		System.out.println("Demo2.run() started....");
		
		try {
			Thread.sleep(5000); // blocked state
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Demo2.run() terminated...");
	}
	
	public static void main(String[] args) {
		Demo2 d2=new Demo2();
		Thread t=new Thread(d2); // new state
		t.start(); // running state
	}

}
