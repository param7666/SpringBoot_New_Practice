package com.tcs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurancy {
	
	public static void main(String[] args) throws Exception {
		ExecutorService service=Executors.newFixedThreadPool(5);
		
		for(int i=1; i<=10;i++) {
			int taskId=i;
			
			service.submit(()->{
			    System.out.println("Task " + taskId + " started");

			    try {
			        Thread.sleep(2000);
			    } catch (InterruptedException e) {
			        Thread.currentThread().interrupt();
			    }

			    System.out.println("Task " + taskId + " completed");

			});
			
		}
		service.shutdown();
	}

}
