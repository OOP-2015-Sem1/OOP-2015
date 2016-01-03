package main;

import main.Polynomial;

public class Functions {

	private static int max(int a, int b) {
		if (a > b)
			return a;
		else
			return b;
	}

	private static int min(int a, int b) {
		if (a < b)
			return a;
		else
			return b;
	}

	public static Polynomial ADD(Polynomial a, Polynomial b) {
		int degC = max(a.getDegree(), b.getDegree());
		Polynomial c = new Polynomial(degC);
		c.initArray();

		int[] A = a.getPol();
		int[] B = b.getPol();

		for (int i = 0; i < min(a.getDegree(), b.getDegree()); i++) {
			int temp = A[i] + B[i];
			c.updatePol(i, temp);
		}
		for (int i = min(a.getDegree(), b.getDegree()); i < degC; i++) {
			int temp;
			if (a.getDegree() > b.getDegree()) {
				temp = A[i];
			} else {
				temp = B[i];
			}
			c.updatePol(i, temp);
		}
		return c;
	}

	public static Polynomial SUBSTRACT(Polynomial a, Polynomial b) {
		int degC = max(a.getDegree(), b.getDegree());
		Polynomial c = new Polynomial(degC);
		c.initArray();

		int[] A = a.getPol();
		int[] B = b.getPol();

		for (int i = 0; i < min(a.getDegree(), b.getDegree()); i++) {
			int temp = A[i] - B[i];
			c.updatePol(i, temp);
		}
		for (int i = min(a.getDegree(), b.getDegree()); i < degC; i++) {
			int temp;
			if (a.getDegree() > b.getDegree()) {
				temp = A[i];
			} else {
				temp = -B[i];
			}
			c.updatePol(i, temp);
		}

		return c;
	}
}