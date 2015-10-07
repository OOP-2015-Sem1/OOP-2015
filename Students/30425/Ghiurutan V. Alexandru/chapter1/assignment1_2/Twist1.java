package chapter1.assignment1_2;

public class Twist1 {
	/**
	 * The next 2 private instance variables are used for the space optimized
	 * method for finding the n-th term from the Fibonacci sequence.
	 */
	private static int first = 0;
	private static int second = 1;

	/**
	 * 
	 * @param n
	 * @return f[n] The function has the time complexity of O(n) and the space
	 *         complexity of O(n).
	 */
	public static int fib(int n) {
		int[] f = new int[n + 1];
		f[0] = 0;
		f[1] = 1;
		for (int i = 2; i <= n; i++) {
			f[i] = f[i - 2] + f[i - 1];
		}
		return f[n];
	}

	/**
	 * @param n
	 * @return fib[n] Instead of storing an entire array with the Fibonacci's
	 *         terms we will store only the previous two numbers,because that is
	 *         all we need. The function has the time complexity of O(n) and the
	 *         space complexity of O(1).
	 */
	public static int fib1() {
		int c;
		c = first + second;
		first = second;
		second = c;
		return second;
	}

	public static boolean isEven(int n) {
		if (n % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static int sum(int bound) {
		int sum = 0;
		for (int i = 2, term = fib(i); term < bound; i++, term = fib(i)) {
			if (isEven(term)) {
				sum += term;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		int bound = 4000000;
		System.out
				.println("The Fibonacci sum of even terms that does not exceed " + bound + " is: " + sum(bound) + ".");
	}

}
