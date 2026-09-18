package com.tcs;

public class Counter {
	
	static int count=1;
	
	public static void main(String[] args) throws Exception{
		Thread t=new Thread(()->{
			for(int i=0;i<1000;i++) {
				synchronized (Counter.class) {
					count++;
				}
			}
		});
		
		Thread t2=new Thread(()->{
			for(int i=0;i<1000;i++) {
				synchronized (Counter.class) {
					count++;
				}
			}
		});
		
		t.start();
		t2.start();
		t.join();
		t2.join();
		System.out.print("Final value of count is "+count);
	}

}
