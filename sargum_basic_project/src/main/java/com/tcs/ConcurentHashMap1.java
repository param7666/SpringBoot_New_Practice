package com.tcs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurentHashMap1 {
	
	public static void main(String[] args) throws InterruptedException {
		ConcurrentHashMap<String, Integer> map=new ConcurrentHashMap<String, Integer>();
		
		Runnable task=()->{
			 	map.merge("Java", 1, Integer::sum);
	            map.merge("Java", 1, Integer::sum);
	            map.merge("Java", 1, Integer::sum);

	            map.merge("Spring", 1, Integer::sum);
	            map.merge("Spring", 1, Integer::sum);
		};
		
		Thread t1=new Thread(task);
		Thread t2=new Thread(task);
		Thread t3=new Thread(task);
		Thread t4=new Thread(task);
		Thread t5=new Thread(task);
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
		
		 System.out.println("Java = " + map.get("Java"));
	     System.out.println("Spring = " + map.get("Spring"));
		
	}

}
