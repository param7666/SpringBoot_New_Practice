package com.tcs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter2 {
	
	static int count=0;
	static Lock lock= new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException{
		Thread t1=new Thread(()->{
			
			for(int i=0;i<=1000;i++) {
				lock.lock();
				try {
					count++;
				} finally {
					lock.unlock();
				}
			}
		});
		
		Thread t2=new Thread(()->{
			
			for(int i=0;i<=1000;i++) {
				lock.lock();
				try {
					count++;
				} finally {
					lock.unlock();
				}
			}
		});
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		
		System.out.println("Final value of count is "+count);
	}

}
