import java.util.Scanner;

public class FibonacciTwist1 {

	public static void main(String[] args) {

		final Scanner enteredNum = new Scanner(System.in);
		System.out.println("Please enter a number: ");
		int number = enteredNum.nextInt();
		int fibTerm = number;
		int a = 1; // Fib(m)
		int b = 0; // Fib(m-1)
		int c = 0; // Fib(m-2)

		while (a < fibTerm) {
			c = b;
			b = a;
			a = b + c;

		}

		System.out.println("The sum of Fibonacci terms  < " + number + "is: " + a);
	}

}
