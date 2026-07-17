package com.tcs.multithreading;

public class MultipleThread implements Runnable{

	@Override
	public void run() {
		System.out.println("MultipleThread.run() started..."+Thread.currentThread().getName());
		try {
			Thread.sleep(5000);
			System.out.println();
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("MultipleThread.run() Terminated..."+Thread.currentThread().getName());
		
	}
	
	public static void main(String[] args) {
		MultipleThread t=new MultipleThread();
		Thread t1=new Thread(t);
		t1.setPriority(Thread.MAX_PRIORITY); //10
		t1.setName("thread t1");
		
		Thread t2=new Thread(t);
		t2.setPriority(Thread.NORM_PRIORITY); //5
		t2.setName("Thread t2");
		
		
		Thread t3=new Thread(t);
		t3.setPriority(Thread.MIN_PRIORITY); //1
		t3.setName("Thread t3");
		
		t1.start();
		t2.start();
		t3.start();
	}

}
