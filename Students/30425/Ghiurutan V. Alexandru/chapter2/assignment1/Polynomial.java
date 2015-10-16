package chapter2.assignment1;

import java.text.DecimalFormat;

public class Polynomial {
	private double[] coeffs;

	public Polynomial(double... coeffs) {
		this.coeffs = coeffs;
	}

	public double[] getPolynomial() {
		return this.coeffs;
	}

	public int getDegree() {
		int deg = 0;
		for (int i = 0; i < this.coeffs.length; i++) {
			if (i != 0) {
				deg = i;
			}
		}
		return deg;
	}

	@Override
	public String toString() {
		String s = "";
		DecimalFormat f = new DecimalFormat();
		for (int i = this.getDegree(); i >= 0; i--) {
			if (i == 0) {
				s += f.format(this.coeffs[i]);
			} else if (i == 1) {
				s += f.format(this.coeffs[i]) + "X" + (coeffs[i - 1] < 0 ? "" : "+");
			} else {
				s += f.format(this.coeffs[i]) + "X^" + i + (coeffs[i - 1] < 0 ? "" : "+");
			}
		}
		return s;
	}
}
