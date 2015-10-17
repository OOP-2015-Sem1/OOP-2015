/**
 * 
 */
package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		System.out.println("Enter max value:");
		Scanner in = new Scanner(System.in);
		int max = in.nextInt();

		int result = 3 * sum(max / 3) + 5 * sum(max / 5) - 15 * sum(max / 15);

		in.close();
		System.out.println(result);
	}

	public static int sum(int n) {
		// sum of first n integers...
		return (int) (n * (n + 1) / 2);
	}
}
