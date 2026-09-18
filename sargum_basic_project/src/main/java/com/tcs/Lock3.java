package com.tcs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock3 {
	
	static Lock lock= new ReentrantLock();
	
	public static void main(String [] args) throws Exception{
		Thread t1=new Thread(()->{
			lock.lock();
			try {
				System.out.println("Thread one accured the lock");
				Thread.sleep(3000);
				System.out.println("Thread one finished");
			} catch(Exception e) {
				Thread.currentThread().interrupt();
			} finally {
				lock.unlock();
				System.out.println("Thread one released lock");
				
			}
		});
		
		
		Thread t2=new Thread(()->{
			try {
				
			System.out.println("Thread 2 trying to accure lock");
			if(lock.tryLock(1, TimeUnit.SECONDS)) {
				try {
					System.out.println("Thread 2 accured lock");
				}finally {
					lock.unlock();
					System.out.println("Thread 2 released lock");
				
				}
			} else {
				System.out.println("thread 2 not able to accure the lock");
			}
			}catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		});
		
		t1.start();
		Thread.sleep(100);
		t2.start();
		t1.join();
		t2.join();
		
		System.out.println("Main thread execution complete");
	}
	

}
