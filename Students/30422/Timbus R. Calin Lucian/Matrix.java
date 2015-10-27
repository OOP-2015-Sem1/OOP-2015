import java.math.BigDecimal;
import java.util.Scanner;

public class Matrix {
	final static int n = 5;

	public static void main(String args[]) {
		BigDecimal[][] a = new BigDecimal[n][n];
		BigDecimal[][] b = new BigDecimal[n][n];
		instantiateMatrix(a);
		instantiateMatrix(b);
		displayMatrix(a);
		displayMatrix(b);
		BigDecimal[][] c = add(a, b);
		displayMatrix(c);
		c = subtract(a, b);
		displayMatrix(c);
		c = multiplyScalar(a);
		displayMatrix(c);
		System.out.println("Are a and b equal?  =>>>" + areEqual(a, b));
		System.out.println("Is 'a' an identity matrix? =>>> " + isIdentityMatrix(a));
		System.out.println("The total degree is : " + fillDegree(a) + "%");
		instantiateMatrix(a);
		instantiateMatrix(b);
		BigDecimal[][] d = multiplyMatrix(a, b);
		displayMatrix(d);
		BigDecimal determinant = determinant(a);
		System.out.println(determinant);
	}

	public static BigDecimal determinant(BigDecimal[][] c) {
		BigDecimal sum = new BigDecimal("0.0");
		BigDecimal s;
		int i, k, l;
		if (c.length == 1)
			return c[0][0];

		for (i = 0; i < c.length; i++) {
			BigDecimal[][] smaller = new BigDecimal[c.length - 1][c.length - 1];
			for (k = 1; k < c.length; k++)
				for (l = 0; l < c.length; l++) {
					if (l < i)
						smaller[k - 1][l] = c[k][l];
					else if (l > i)
						smaller[k - 1][l - 1] = c[k][l];
				}
			if (i % 2 == 0)
				s = new BigDecimal("1");
			else
				s = new BigDecimal("-1");
			sum = sum.add(s.multiply(c[0][i]));

		}
		return sum;
	}

	public static BigDecimal[][] instantiateMatrix(BigDecimal[][] a) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int number = Integer.parseInt("1");
				a[i][j] = new BigDecimal(number);
			}
		return a;
	}

	public static void displayMatrix(BigDecimal[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println(" ");
		}
	}

	public static boolean isZeroMatrix(BigDecimal[][] a) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a.length; j++) {
				if (a[i][j].intValue() != 0) {
					return false;
				}
			}
		return true;
	}

	public static BigDecimal[][] add(BigDecimal a[][], BigDecimal b[][]) {
		BigDecimal[][] c = new BigDecimal[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				c[i][j] = a[i][j].add(b[i][j]);
			}
		return c;

	}

	public static BigDecimal[][] subtract(BigDecimal a[][], BigDecimal b[][]) {
		BigDecimal[][] c = new BigDecimal[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				c[i][j] = a[i][j].subtract(b[i][j]);
			}

		return c;

	}

	public static BigDecimal[][] multiplyScalar(BigDecimal a[][]) {
		a = new BigDecimal[n][n];
		final int scalar = 10;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				int number = Integer.parseInt("1");
				a[i][j] = new BigDecimal(number * scalar);
			}
		return a;
	}

	public static boolean areEqual(BigDecimal a[][], BigDecimal b[][]) {
		a = new BigDecimal[n][n];
		b = new BigDecimal[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (a[i][j] != b[i][j]) {
					return false;
				}
			}
		return true;
	}

	public static boolean isIdentityMatrix(BigDecimal a[][]) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (a[i][j].intValue() != 1) {
					return false;
				}
			}
		return true;
	}

	public static double fillDegree(BigDecimal a[][]) {
		int totalNumbers = n * n;
		int count = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (a[i][j].intValue() == 0) {
					count++;
				}
			}
		if (count == 0)
			return 100;
		else
			return (count / totalNumbers);
	}

	public static BigDecimal[][] multiplyMatrix(BigDecimal[][] a, BigDecimal[][] b) {
		BigDecimal[][] c = new BigDecimal[n][n];
		for (int p = 0; p < c.length; p++)
			for (int m = 0; m < c.length; m++) {
				c[p][m] = new BigDecimal(0);
			}
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a.length; j++) {
				int k = 0;
				int sum = 0;
				while (k < n) {
					sum += ((a[i][j].intValue() * b[k][j].intValue()));
					k++;
				}
				c[i][j] = new BigDecimal(sum);
			}
		return c;
	}
}
