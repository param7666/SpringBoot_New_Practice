package com.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ArraysTest {
	
	public static Scanner sc=new Scanner(System.in);
	
	public static void createArray() {
		int n=sc.nextInt();
		int arr[]=new int[n];
		for(int i=0;i<n;i++) {
			arr[i]=sc.nextInt();
			//System.out.println(arr[i]);
		}
		for(int i=arr.length-1;i>=0;i--) {
			System.out.print(arr[i]+" ");
		}
		
	}
	
	
	public static boolean isPrime(int n) {
		int count=0;
		for(int i=1;i<=n;i++) {
			if(n%i==0) count++;
		}
		
		if(count==2) return true;
		else return false;
	}
	
	
	public static void printPrime() {
		int arr[]= {1,2,3,7,9,5,6,};
		for(int i=0;i<arr.length;i++) {
			if(isPrime(arr[i])) {
				//System.out.println(arr[i]);
			} else {
				System.out.println(arr[i]);
			}
		}
		
	}
	
	
	public static void findMinElement() {
		int arr[]= {1,2,3,7,9,5,6,};
		Arrays.sort(arr);
		System.out.println("min:: "+arr[0]+" maax:: "+arr[arr.length-1]);
	}
	
	
	public static void searchElement() {
		int arr[]= {1,2,3,7,9,5,6,};
		int search=3;
//		for(int i: arr) {
//			if(i==search) {
//				System.out.println("Present");	
//			}
		
		for(int i=0;i<arr.length;i++) {
			if(arr[i]==search) System.out.println("element is found at index num"+ i);
		}
	}
	
	
	public static void removeDuplicate() {
		int arr[]= {1,2,3,7,9,5,6,2,3,4,7,9};
		Set<Integer> set=new LinkedHashSet<Integer>();
		
		for(int i:arr) {
			set.add(i);
		}
		
		int arr2[]=new int[set.size()];
		int index=0;
		for(int s:set) {
			arr2[index]=s;
			index++;
		}
		
		System.out.println(set);
		//System.out.println(arr2);
		for(int a:arr2) System.out.print(a+" ");
		
	}
	
	public static void countFreq() {
		int arr[]= {1,2,1,3,7,9,5,6,2,3,4,7,9,9,9,9,2,2,2,5,5,5};
		List<Integer> list=new ArrayList<Integer>(); //1 
		//1,2,1,3,7,9,5,6,2,3,4,7,9,9,9,9,2,2,2,5,5,5
		for(int i=0;i<arr.length;i++) {
			int count=0;
			if(!list.contains(arr[i])) {
			for(int j=i;j<arr.length;j++) {
				if(arr[i]==arr[j]) {
					count++;
					if(!list.contains(arr[i])) list.add(arr[i]);
				}
				
			}
		}
			
			if(count!=0)
			System.out.println(arr[i]+" count "+count);
		}
		
//		System.out.println(list);
	}
	
	
	
	public static void maxCountFreq() {
		int arr[]= {1,2,1,3,7,9,5,6,2,3,4,7,9,9,9,9,9,9,9,9,9,2,2,2,5,5,5};
		List<Integer> list=new ArrayList<Integer>();
		Map<Integer, Integer> map=new HashMap<Integer, Integer>();
		for(int i=0;i<arr.length;i++) {
			int count=0;
			if(!list.contains(arr[i]))
			for(int j=i;j<arr.length;j++) {
				if(arr[i]==arr[j]) {
					count++;
					if(!list.contains(arr[i])) {
						list.add(arr[i]);
					}
				}
			}
			if(count!=0) {
				//System.out.println(arr[i]+" count "+count);
				map.put(arr[i], count);
			}
		}
		
		System.out.println(map);
		Integer key=map.entrySet()
        .stream()
        .max(Map.Entry.comparingByValue())
        .get()
        .getKey();
		System.out.println(key);
	}
	
	
	public static void moveZero() {
		int arr[]= {1,0,2,0,1,3,0,7};
		
		List<Integer> list=new ArrayList<Integer>(); //1 2 1 3 7 
	
		System.out.println(list);
									   //
		int ans[]=new int[arr.length];// 1 2 1 3 7 0 0 0 
		
		for(int i=0;i<list.size();i++) ans[i]=list.get(i); 
		
		for(int an:ans) System.out.print(an+" ");

	}
	
	public static void moveNegetive() {
		int arr[]= {1,-1,2,-2,1,3,-3,7};
		List<Integer> negetive=new ArrayList<Integer>();
		List<Integer> positive=new ArrayList<Integer>();
		
		for(int n:arr) {
			if(n>=0) {
				positive.add(n);
			} else {
				negetive.add(n);
			}
		}
		System.out.println(negetive); System.out.println(positive);
		
		int ans[]=new int[arr.length];
		
		for(int i=0;i<negetive.size();i++) {
			ans[i]=negetive.get(i);
		}
		
		
		System.out.println(ans.length+" "+negetive.size()+"  "+positive.size());
		
		for(int i=negetive.size();i<ans.length;i++) 
			
			ans[i]=positive.get(i-(negetive.size()));
		
		for(int n:ans) System.out.print(n+" ");
		
	}
	
	
public static void main(String[] args) {
	
//	createArray();
//	printPrime();	
//	findMinElement();
//	searchElement();
//	removeDuplicate();
//	countFreq();
//	maxCountFreq();
//	moveZero();
//	moveNegetive();
	
	
}
}





