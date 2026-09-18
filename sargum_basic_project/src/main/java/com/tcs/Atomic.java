package com.tcs;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomic {
	
	static AtomicInteger count=new AtomicInteger(0);
	
	public static void main(String[] args) throws InterruptedException {
//		count.incrementAndGet();
//		System.out.println(count.get());
		
		Thread t1=new Thread(()->{
			for(int i=0;i<=1000;i++) {
				count.incrementAndGet();
			}
		});
		Thread t2=new Thread(()->{
			for(int i=0;i<=1000;i++) {
				count.incrementAndGet();
			}
		});
		Thread t3=new Thread(()->{
			for(int i=0;i<=1000;i++) {
				count.incrementAndGet();
			}
		});
		Thread t4=new Thread(()->{
			for(int i=0;i<=1000;i++) {
				count.incrementAndGet();
			}
		});
		Thread t5=new Thread(()->{
			for(int i=0;i<=1000;i++) {
				count.incrementAndGet();
			}
		});

		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		
		
		System.out.println("Final value of count is :"+count.get());
		
		
	}

}
