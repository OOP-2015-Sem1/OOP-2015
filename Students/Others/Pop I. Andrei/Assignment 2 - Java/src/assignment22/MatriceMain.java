package assignment22;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Scanner;

public class MatriceMain {

	public static BigDecimal[][] readFromFile(Scanner read) {

		int n = read.nextInt();
		int m = read.nextInt();
		BigDecimal[][] a = new BigDecimal[n][m];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				a[i][j] = read.nextBigDecimal();
			}

		return a;

	}

	public static void print(BigDecimal[][] a) {

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}

	}

	public static void main(String[] args) {

		Scanner read = null;

		try {
			read = new Scanner(new File("matrix.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BigDecimal[][] a = readFromFile(read);
		BigDecimal[][] b = readFromFile(read);
		
		BigDecimal scalar = new BigDecimal(10);
		
		MatrixOperations op = new MatrixOperations();
		MatrixOperations op1 = new MatrixOperations();
		
		//BigDecimal det = op.determinant(a);
		
		//System.out.println(det);
		//System.out.println(op1.fillDegree(a));
		
		op.solveSystem(a);
		
		//BigDecimal[][] r = MatrixOperations.multByScalar(a, scalar);
		
		//print(r);
	}
	

}
