package chapter1.assignment1_2;

public class FibonacciEvenSum {
	public boolean isEven(long n) {
		if (n % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
/**
 * 
 * @param n
 * @return fib(n)
 * Time complexity T(n)=T(n-1)+T(n-2) which is exponential.
 */
	public long fib(long n) {
		if (n <= 1) {
			return n;
		} else {
			return fib(n - 2) + fib(n - 1);
		}
	}

	public long sum(long bound) {
		long sum = 0;
		for (long n = 0, term = fib(n); term < bound; ++n, term = fib(n)) {
			if (isEven(term)) {
				sum += term;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		FibonacciEvenSum f = new FibonacciEvenSum();
		System.out.println("The Fibonacci even term sum is :" + f.sum(4000000) + ".");
	}
}
