package chapter2.assignment2;

import java.text.DecimalFormat;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.util.Scanner;

public class Operations {
	private static Scanner in;

	public Operations() {
		super();
	}

	private void printMatrix(BigDecimal[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				out.print(a[i][j] + " ");
			}
			out.println();
		}
	}

	private BigDecimal[][] readMatrix(String nr) {
		in = new Scanner(System.in);
		BigDecimal[][] a;
		out.println("Give the number of rows and the number of columns of the " + nr + " matrix.");
		int rowsA = in.nextInt();
		int columnsA = in.nextInt();
		if (rowsA == 0 || columnsA == 0) {
			throw new IllegalArgumentException("You set as 0 one or more matrix arguments.");
		} else if (rowsA != columnsA) {
			throw new IllegalArgumentException("You didn't entered a square matrix.");
		} else {
			a = new BigDecimal[rowsA][columnsA];
			out.println("Enter the " + nr + " matrix:");
			in.nextLine();
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[0].length; j++) {
					out.printf("a[%d][%d]= \n", i, j);
					String s = in.nextLine();
					a[i][j] = new BigDecimal(s);
				}
			}
			return a;
		}
	}

	public void action(MatrixOperations op) {
		Operations f = new Operations();
		BigDecimal[][] A = readMatrix("first");
		BigDecimal[][] B = readMatrix("second");
		out.println("A+B=");
		f.printMatrix(MatrixOperations.add(A, B));
		out.println("A-B=");
		f.printMatrix(MatrixOperations.subtract(A, B));
		out.println("A*B=");
		f.printMatrix(MatrixOperations.multiply(A, B));
		out.println("Please enter the scalar to multiply our matrices:");
		BigDecimal x = new BigDecimal(in.nextLine());
		out.println(x + "*A=");
		f.printMatrix(MatrixOperations.multiplyScalar(A, x));
		out.println(x + "*B=");
		f.printMatrix(MatrixOperations.multiplyScalar(B, x));
		out.println("The determinant of the first matrix A=" + MatrixOperations.determinant(A));
		out.println("The determinant of the second matrix B=" + MatrixOperations.determinant(B));
		out.println(MatrixOperations.areEqual(A, B) ? "Matrix A is equal with B." : "Matrix A is not equal with B.");
		out.println(MatrixOperations.isZeroMatrix(A) ? "Matrix A is a zero matrix." : "Matrix A is not a zero matrix.");
		out.println(MatrixOperations.isZeroMatrix(B) ? "Matrix B is a zero matrix." : "Matrix B is not a zero matrix.");
		out.println(MatrixOperations.isIdentityMatrix(A) ? "Matrix A is an identity matrix."
				: "Matrix A is not an identity matrix.");
		out.println(MatrixOperations.isIdentityMatrix(B) ? "Matrix B is an identity matrix."
				: "Matrix B is not an identity matrix.");
		out.println(
				"Matrix A has the fill degree of " + new DecimalFormat().format(MatrixOperations.fillDegree(A)) + "%.");
		out.println(
				"Matrix B has the fill degree of " + new DecimalFormat().format(MatrixOperations.fillDegree(B)) + "%.");
	}
}
