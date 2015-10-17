package main;

import main.Polynomial;

public class Functions {

	private int max(int a, int b) {
		if (a > b)
			return a;
		else
			return b;
	}

	public Polynomial ADD(Polynomial a, Polynomial b) {
		int degC = max(a.getDegree(), b.getDegree());
		Polynomial c = new Polynomial(degC);
		c.initArray();

		int[] A = a.getPol();
		int[] B = b.getPol();

		for (int i = 0; i < degC; i++) {
			int temp = A[i] + B[i];
			c.updatePol(i, temp);
		}
		return c;
	}
}
