import java.util.*;

/**
 * 
 * @author nagyhanna007 Fibonacci series : 1 1 2 3 5 8 13 ... or 0 1 1 2 3 5 8
 *         13 ...
 * Implementing Fibonacci calculator in O(logN) time
 * and teh sum of the even-valued terms of it;
 *
 */

public class FibonacciSeries {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Give a positive natural number: ");

		int number = input.nextInt();
		if (number < 0) {
			System.out.println("You did not give a positive number! ");
		} else {
			System.out.print("The sum of the first " + number + "th even-valued terms from fibonacci is ");

			System.out.println(" "+getSumOfEvens(number));
			System.out.println("The first "+number+"th term from Fibonacci series: ");
			display(number);
			
		}
		input.close();
	}

	public static int getSumOfEvens(int number) {
		int sum = 0;
		for (int i = 1; i <= number; i++) {
			if (getNthFibonacci(i) % 2 == 0)
				sum += getNthFibonacci(i);
		}
		return sum;
	}

	 /*         n
     * [ 1 1 ]     [ F(n+1) F(n)   ]
     * [ 1 0 ]   = [ F(n)   F(n-1) ]
     * Formula for Fibonacci series;
     * Where F(n) denotes the n-th number in Fibonacci series;
     */

	public static int getNthFibonacci(int n) {

		int[][] iMatrix = { { 1, 0 }, { 0, 1 } }; // Identity matrix;
		int[][] fMatrix = { { 1, 1 }, { 1, 0 } };
		if (n <= 1)
			return n;
		while (n > 0) {
			if (n % 2 == 1) {
				multiplyMatrix(iMatrix, fMatrix);
			}
			n /= 2;
			multiplyMatrix(fMatrix, fMatrix);

		}

		return iMatrix[1][0];

	}

	public static void multiplyMatrix(int[][] m, int[][] n) {

		int a = m[0][0] * n[0][0] + m[0][1] * n[1][0];
		int b = m[0][0] * n[0][1] + m[0][1] * n[1][1];
		int c = m[1][0] * n[0][0] + m[1][1] * n[1][0];
		int d = m[1][0] * n[0][1] + m[1][1] * n[1][1];

		m[0][0] = a;
		m[0][1] = b;
		m[1][0] = c;
		m[1][1] = d;
	}

	public static void display(int n)
	{
		for(int i=1;i<=n;i++)
		System.out.print("  "+getNthFibonacci(i));
	}
}
