package chapter1.assignment1_2;

public class Twist2 {
	public int fib(int n) {
		int[][] f = { { 1, 1 }, { 1, 0 } };
		if (n == 0) {
			return n;
		}
		f = power(f, n - 1);
		return f[0][0];
	}

	public int[][] power(int[][] f, int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Not a valid power of matrix.");
		} else if (n <= 1) {
			return f;
		} else {
			int[][] result = { { 1, 0 }, { 0, 1 } };
			while (n > 0) {
				if (n % 2 == 1) {
					result = multiply(result, f);
				}
				n /= 2;
				f = multiply(f, f);
			}
			return result;
		}
	}

	public int[][] multiply(int[][] a, int[][] b) {
		int w = a[0][0] * b[0][0] + a[0][1] * b[1][0];
		int x = a[0][0] * b[0][1] + a[0][1] * b[1][1];
		int y = a[1][0] * b[0][0] + a[1][1] * b[1][0];
		int z = a[1][0] * b[0][1] + a[1][1] * b[1][1];
		int[][] f = { { w, x }, { y, z } };
		return f;
	}

	public boolean isEven(int n) {
		if (n % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int sum(int bound) {
		int sum = 0;
		for (int i = 0, term = fib(i); term < bound; i++, term = fib(i)) {
			if (isEven(term)) {
				sum += term;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		Twist2 f = new Twist2();
		System.out.println("The sum of the even value terms from the Fibonacci sequence that do not exceed " + 4000000
				+ " is: " + f.sum(4000000) + ".");
	}
}
