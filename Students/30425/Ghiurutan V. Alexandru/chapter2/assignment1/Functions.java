package chapter2.assignment1;

public class Functions {
	private static final double EPSILON = 0.00001;
	private static final int INFINITY = Integer.MAX_VALUE;
	private static final int ITERATIONS = 888;

	/*
	 * A function that initialize our vector.
	 */
	private double[] fillWithZero(double[] a) {
		for (int i = a.length - 1; i >= 0; i--) {
			a[i] = 0;
		}
		return a;
	}

	/**
	 * 
	 * @param a
	 * @return a clone of the coefficients of the polynomial so that we will not
	 *         modify the polynomial in our operations.
	 */
	private double[] getClone(Polynomial a) {

		double[] p = a.getPolynomial();
		double[] rezult = new double[p.length];
		int degA = a.getDegree();
		for (int i = 0; i <= degA; i++) {
			rezult[i] = p[i];
		}
		return rezult;
	}

	public Polynomial add(Polynomial a, Polynomial b) {
		int degA = a.getDegree();
		int degB = b.getDegree();
		double[] p1 = getClone(a);
		double[] p2 = getClone(b);
		double[] newCoeffs = new double[(degA < degB ? degB + 1 : degA + 1)];
		newCoeffs = fillWithZero(newCoeffs);
		for (int i = 0; i <= degA; i++) {
			newCoeffs[i] += p1[i];
		}
		for (int i = 0; i <= degB; i++) {

			newCoeffs[i] += p2[i];
		}
		return new Polynomial(newCoeffs);
	}

	public Polynomial subtract(Polynomial a, Polynomial b) {
		int degA = a.getDegree();
		int degB = b.getDegree();
		double[] p1 = getClone(a);
		double[] p2 = getClone(b);
		double[] newCoeffs = new double[(degA < degB ? degB + 1 : degA + 1)];
		newCoeffs = fillWithZero(newCoeffs);
		for (int i = 0; i <= degA; i++) {
			newCoeffs[i] = p1[i];
		}
		for (int i = 0; i <= degB; i++) {
			newCoeffs[i] -= p2[i];
		}
		return new Polynomial(newCoeffs);
	}

	public Polynomial multiply(Polynomial a, Polynomial b) {
		int degA = a.getDegree();
		int degB = b.getDegree();
		double[] p1 = getClone(a);
		double[] p2 = getClone(b);
		double[] newCoeffs = new double[degA + degB + 1];
		newCoeffs = fillWithZero(newCoeffs);
		for (int i = 0; i <= degA; i++) {
			for (int j = 0; j <= degB; j++) {
				newCoeffs[i + j] += p1[i] * p2[j];
			}
		}
		return new Polynomial(newCoeffs);
	}

	// Twist 1
	public double[] getNewCoeffs(double value, int degree) {
		double[] coeffs = new double[degree + 1];
		coeffs = fillWithZero(coeffs);
		coeffs[degree] = value;
		return coeffs;
	}

	/**
	 * Polynomial long division implementation.
	 * 
	 * @param a
	 *            dividend
	 * @param b
	 *            divisor
	 * @return an array of Polynomials,where the first element is the quotient
	 *         and the second element is the remainder.
	 */
	public Polynomial[] divide(Polynomial a, Polynomial b) {
		int degA = a.getDegree();
		int degB = b.getDegree();
		if (degA < degB) {
			throw new IllegalArgumentException();
		}
		double[] d = getClone(a);// This will be our remainder
		double[] p2 = getClone(b);// A clone to the second Polynomial used in
									// the for loop
		double[] quotient = new double[degA - degB + 1];
		for (int i = degA; i >= degB; i--) {
			if (degB > (new Polynomial(d).getDegree())) {
				break;
			}
			quotient[i - degB] = d[i] / p2[degB];
			double[] q = getNewCoeffs(quotient[i - degB], i - degB);
			d = subtract((new Polynomial(d)), multiply(new Polynomial(q), b)).getPolynomial();
		}
		Polynomial[] p = new Polynomial[] { new Polynomial(quotient), new Polynomial(d) };
		return p;
	}

	public double evaluate(Polynomial a, double x) {
		int degA = a.getDegree();
		double rezult = 0;
		double[] p = getClone(a);
		for (int i = 0; i <= degA; i++) {
			rezult += p[i] * Math.pow(x, i);
		}
		return rezult;
	}

	public Polynomial scalarMul(Polynomial a, double x) {
		double[] p = getClone(a);
		int degA = a.getDegree();
		for (int i = 0; i <= degA; i++) {
			p[i] *= x;
		}
		return new Polynomial(p);
	}

	// Twist 2
	private boolean diffSign(Polynomial p1, double a, double b) {
		double rez1 = evaluate(p1, a);
		double rez2 = evaluate(p1, b);
		return ((rez1 > 0 && rez2 < 0) || (rez1 < 0 && rez2 > 0));

	}

	private double[] getLimits(Polynomial a) {
		double[] end = new double[2];
		end[0] = Math.random() * 100;
		int n = 0;
		// if the first randomly generated number gives positive result in
		// polynomial
		if (evaluate(a, end[0]) > 0) {
			end[1] = Math.random() * 100;
			while (evaluate(a, end[1]) > 0 && n < ITERATIONS) {
				if (n % 2 == 0) {
					end[1] = Math.random() * ((-0 * Math.random()) * 100);
				} else {
					end[1] = Math.random() * ((100 * Math.random()) * 100);
				}
				n++;
			}
		} else {
			end[1] = Math.random() * 100;
			while (evaluate(a, end[1]) < 0 && n < ITERATIONS) {
				if (n % 2 == 0) {
					end[1] = Math.random() * ((-100 * Math.random()) * 100);
				} else {
					end[1] = Math.random() * ((100 * Math.random()) * 100);
				}
				n++;
			}
		}
		// if the maximum number of operations were to be reached
		if (n == ITERATIONS - 1) {
			end[0] = INFINITY;
			end[1] = INFINITY;
		}
		return end;

	}

	/**
	 * 
	 * Bisection method for approximating the root of the polynomial.
	 * 
	 * @param a
	 * @return the approximated value for the root.
	 */

	public double bisection(Polynomial p) {
		double[] limits = getLimits(p);
		double a = limits[0];
		double b = limits[1];
		while ((b - a) > EPSILON) {
			double m = (a + b) / 2;
			if (evaluate(p, m) == 0) {
				return m;
			}
			if (diffSign(p, a, m)) {
				b = m;
			} else {
				a = m;
			}
		}
		return (a + b) / 2;
	}

}
