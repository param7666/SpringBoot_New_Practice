package com.string;

//Find the longest palindromic substring in a given string.
public class LongestPalindrome {
	
	public static boolean isPalindrome(String s) {
		StringBuilder sb=new StringBuilder(s).reverse();
		if(s.equals(sb.toString())) return true;
		else return false;
		
	}
	
	public static String longestPalindrome(String str) {
		if(str==null || str.isEmpty()) {
			//System.out.println("String is null or empty");
			return "";
		}
		String longest="";
		for(int i=0;i<str.length();i++) {
			for(int j=i+1;j<=str.length();j++) {
				String subString=str.substring(i,j);
				if(isPalindrome(subString) && subString.length()>longest.length()) {
					longest=subString;
				}
			}
		}
		//System.out.println(longest);
		return longest;
	}

	public static void main(String[] args) {
		String[] testCases = {"babad", "cbbd", "a", "ac", "racecarxyz"};

        for (String test : testCases) {
            System.out.println("Input: " + test + " -> Longest Palindrome: " + longestPalindrome(test));
        }
	}
}
