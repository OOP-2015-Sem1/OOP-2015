package chapter1.assignment1_4;

import static java.lang.System.out;
import java.text.DecimalFormat;

public class PrimeSpiral {
	/**
	 * 
	 * @param x
	 * @return the next bottom right element in the matrix ,which will be the
	 *         square of an odd number.
	 * 
	 */
	public int findNext(int x) {
		return (int) Math.pow(((int) Math.sqrt(x) + 2), 2);
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @return the ratio between the prime numbers on the diagonals and the
	 *         total number of elements on the diagonal in percents.
	 */
	public int computeRatio(double a, double b) {
		return (int) ((a / b) * 100);
	}

	/**
	 * 
	 * @param n
	 * @return true if the number is prime.
	 */
	public boolean isPrime(int n) {
		if (n < 2) {
			return false;
		} else {
			for (int i = 2; i <= n / 2; i++) {
				if (n % i == 0) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 
	 * @param first
	 * @param last
	 * @return an int vector. The function counts the number of prime numbers on
	 *         the both diagonals on specific intervals.
	 */
	public int[] underBound(int first, int last) {
		int total = 0, primeNr = 0, currentElem = last;
		int step = (int) Math.sqrt(last) - 1;
		while (currentElem > first) {
			total++;
			if (isPrime(currentElem)) {
				primeNr++;
			}
			currentElem -= step;
			if (currentElem == 1) {
				total++;
			}
		}
		int[] v = { total, primeNr };
		return v;
	}

	/**
	 * The function that checks for a ratio under 10%.The virtual matrix extends
	 * if we can't find the desired ratio.
	 */
	public void underTen() {
		int first = 1, last = 9;
		int[] v = underBound(first, last);
		int total = v[0], primeNr = v[1], ratio = computeRatio(primeNr, total);
		while (ratio > 10) {
			first = last;
			last = findNext(last);
			v = underBound(first, last);
			total += v[0];
			primeNr += v[1];
			ratio = computeRatio(primeNr, total);
		}
		out.println("The side length of the square matrix for a ratio under 10% is "
				+ new DecimalFormat().format(Math.sqrt(last)) + ".");
	}

	public static void main(String[] args) {
		PrimeSpiral p = new PrimeSpiral();
		p.underTen();
	}
}
