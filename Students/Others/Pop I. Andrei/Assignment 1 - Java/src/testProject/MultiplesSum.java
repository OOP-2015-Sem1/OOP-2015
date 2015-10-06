package testProject;

import java.util.Scanner;

public class multiplesSum {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		int n = in.nextInt();
		
		long sum = 0;
		
		for(int i = 2; i < n; i++) {
			if(i % 5 == 0 || i % 3 == 0)
				sum += i;
		}
		
		System.out.println(sum);
		
		in.close();

	}
	
	
	
	//for an int variable, the overflow will occur when n is ~ 10000000000
	// for a long variable, the overflow will occur when n is ~ 10000000000

}
