package chapter2.assignment2;

import java.math.BigDecimal;

public class MatrixOperations {
	// Twist 1:Singleton Pattern
	/*
	 * The main advantage of the Singleton Pattern is that it allows only a
	 * single creation of object from that specific class.
	 * 
	 */
	private static MatrixOperations instance = null;

	private MatrixOperations() {
	}

	// Lazy instantiation
	public static MatrixOperations getInstance() {
		if (instance == null) {
			instance = new MatrixOperations();
		}
		return instance;
	}

	private static void fillWithZero(BigDecimal[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = BigDecimal.ZERO;
			}
		}
	}

	public static BigDecimal[][] add(BigDecimal[][] a, BigDecimal[][] b) {
		if ((a.length != b.length) || a[0].length != b[0].length) {
			throw new IllegalArgumentException("Different number of rows and columns.");
		}
		int nrLines = a.length;
		int nrColumns = a[0].length;
		BigDecimal[][] c = new BigDecimal[nrLines][nrColumns];
		fillWithZero(c);
		for (int i = 0; i < nrLines; i++) {
			for (int j = 0; j < nrColumns; j++) {
				c[i][j] = a[i][j].add(b[i][j]);
			}
		}
		return c;
	}

	public static BigDecimal[][] subtract(BigDecimal[][] a, BigDecimal[][] b) {
		if ((a.length != b.length) || a[0].length != b[0].length) {
			throw new IllegalArgumentException("Different number of rows and columns.");
		}
		int nrLines = a.length;
		int nrColumns = a[0].length;
		BigDecimal[][] c = new BigDecimal[nrLines][nrColumns];
		fillWithZero(c);
		for (int i = 0; i < nrLines; i++) {
			for (int j = 0; j < nrColumns; j++) {
				c[i][j] = a[i][j].subtract(b[i][j]);
			}
		}
		return c;
	}

	public static BigDecimal[][] multiply(BigDecimal[][] a, BigDecimal[][] b) {
		int mA = a.length;
		int nA = a[0].length;
		int mB = b.length;
		int nB = b[0].length;
		if (nA != mB) {
			throw new RuntimeException("Illegal matrix dimensions for multiplication.");
		}
		BigDecimal[][] c = new BigDecimal[mA][nB];
		fillWithZero(c);
		for (int i = 0; i < mA; i++) {
			for (int j = 0; j < nB; j++) {
				for (int k = 0; k < nA; k++) {
					c[i][j] = c[i][j].add(a[i][k].multiply(b[k][j]));
				}
			}
		}
		return c;
	}

	private static BigDecimal[][] cloneMatrix(BigDecimal[][] a) {
		BigDecimal[][] c = new BigDecimal[a.length][a[0].length];
		fillWithZero(c);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				c[i][j] = a[i][j];
			}
		}
		return c;
	}

	public static BigDecimal[][] multiplyScalar(BigDecimal[][] a, BigDecimal x) {
		int mA = a.length;
		int nA = a[0].length;
		BigDecimal[][] c = new BigDecimal[mA][nA];
		c = cloneMatrix(a);
		for (int i = 0; i < mA; i++) {
			for (int j = 0; j < nA; j++) {
				c[i][j] = c[i][j].multiply(x);
			}
		}
		return c;
	}

	public static BigDecimal determinant(BigDecimal[][] m) {
		BigDecimal sum = BigDecimal.ZERO, s;

		if (m.length == 1) {
			return m[0][0];
		}
		for (int i = 0; i < m.length; i++) {
			BigDecimal[][] smaller = new BigDecimal[m.length - 1][m.length - 1];
			for (int a = 1; a < m.length; a++) {
				for (int b = 0; b < m.length; b++) {
					if (b < i) {
						smaller[a - 1][b] = m[a][b];
					} else if (b > i) {
						smaller[a - 1][b - 1] = m[a][b];
					}
				}
			}
			if (i % 2 == 0) {
				s = BigDecimal.ONE;
			} else {
				s = BigDecimal.ZERO.subtract(BigDecimal.ONE);
			}
			sum = sum.add(s.multiply(m[0][i]).multiply(determinant(smaller)));
		}
		return sum;
	}

	public static boolean isZeroMatrix(BigDecimal[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (!a[i][j].equals(BigDecimal.ZERO)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isIdentityMatrix(BigDecimal[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if ((i == j) && !(a[i][j].equals(BigDecimal.ONE))) {
					return false;
				} else if ((i != j) && !(a[i][j].equals(BigDecimal.ZERO))) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean areEqual(BigDecimal[][] a, BigDecimal[][] b) {
		if ((a.length != b.length) || (a[0].length != b[0].length)) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (!a[i][j].equals(b[i][j])) {
					return false;
				}
			}
		}
		return true;
	}

	public static BigDecimal fillDegree(BigDecimal[][] a) {
		BigDecimal nrOfNonZeroElements = BigDecimal.ZERO;
		String nr = Integer.toString(a.length * a[0].length);
		BigDecimal nrOfElements = new BigDecimal(nr);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (!a[i][j].equals(BigDecimal.ZERO)) {
					nrOfNonZeroElements = nrOfNonZeroElements.add(BigDecimal.ONE);
				}
			}
		}
		BigDecimal rezult = BigDecimal.ZERO;
		try {
			rezult = nrOfNonZeroElements.divide(nrOfElements);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return rezult.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
	}
}
