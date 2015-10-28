package matrix;
import matrix.MatrixOperations;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) 
	{
		BigDecimal[][] firstMatrix = new BigDecimal[4][4];
		BigDecimal[][] secondMatrix = new BigDecimal[4][4];
		BigDecimal[][] thirdMatrix = new BigDecimal[2][2];
		MatrixOperations matrixOperations = MatrixOperations.getInstance();
		//System.out.println(firstMatrix.length);

		firstMatrix[0][0] = new BigDecimal(3);
		firstMatrix[0][1] = new BigDecimal(4);
		firstMatrix[0][2] = new BigDecimal(1);
		firstMatrix[0][3] = new BigDecimal(2);
		firstMatrix[1][0] = new BigDecimal(7);
		firstMatrix[1][1] = new BigDecimal(4);
		firstMatrix[1][2] = new BigDecimal(10);
		firstMatrix[1][3] = new BigDecimal(2);
		firstMatrix[2][0] = new BigDecimal(3);
		firstMatrix[2][1] = new BigDecimal(-2);
		firstMatrix[2][2] = new BigDecimal(9);
		firstMatrix[2][3] = new BigDecimal(10);
		firstMatrix[3][0] = new BigDecimal(5);
		firstMatrix[3][1] = new BigDecimal(10);
		firstMatrix[3][2] = new BigDecimal(2);
		firstMatrix[3][3] = new BigDecimal(9);

		secondMatrix[0][0] = new BigDecimal(4);
		secondMatrix[0][1] = new BigDecimal(5);
		secondMatrix[0][2] = new BigDecimal(-2);
		secondMatrix[0][3] = new BigDecimal(3);
		secondMatrix[1][0] = new BigDecimal(3);
		secondMatrix[1][1] = new BigDecimal(4);
		secondMatrix[1][2] = new BigDecimal(4);
		secondMatrix[1][3] = new BigDecimal(4);
		secondMatrix[2][0] = new BigDecimal(5);
		secondMatrix[2][1] = new BigDecimal(10);
		secondMatrix[2][2] = new BigDecimal(9);
		secondMatrix[2][3] = new BigDecimal(8);
		secondMatrix[3][0] = new BigDecimal(6);
		secondMatrix[3][1] = new BigDecimal(5);
		secondMatrix[3][2] = new BigDecimal(-3);
		secondMatrix[3][3] = new BigDecimal(5);
		
		thirdMatrix[0][0] = new BigDecimal(1);
		thirdMatrix[0][1] = new BigDecimal(2);
		thirdMatrix[1][0] = new BigDecimal(1);
		thirdMatrix[1][1] = new BigDecimal(4);
		
		System.out.println("First Matrix :");
		matrixOperations.prettyPrintMatrix(firstMatrix);
		System.out.println();
		System.out.println("Second Matrix :");
		matrixOperations.prettyPrintMatrix(secondMatrix);
		System.out.println();
		
		BigDecimal[][] result = new BigDecimal[4][4];
		BigDecimal determinantResult;
		
		System.out.println("Adition result:");
		result = matrixOperations.add(firstMatrix, secondMatrix);
		matrixOperations.prettyPrintMatrix(result);
		System.out.println();
		
		System.out.println("Subtraction result:");
		result = matrixOperations.subtract(firstMatrix, secondMatrix);
		matrixOperations.prettyPrintMatrix(result);
		System.out.println();
		
		System.out.println("Multiplication result:");
		result = matrixOperations.multiply(firstMatrix, secondMatrix);
		matrixOperations.prettyPrintMatrix(result);
		System.out.println();
		
		System.out.println("Multiplication by scalar "+"-22 "+"result:");
		result = matrixOperations.multiplyScalar(firstMatrix,BigDecimal.valueOf(-22));
		matrixOperations.prettyPrintMatrix(result);
		System.out.println();
		
		System.out.println("Determinat of first matrix result:");
		//System.out.println(firstMatrix.length);
		determinantResult = matrixOperations.determinant(firstMatrix);
		System.out.println(determinantResult);
		System.out.println();
		
		System.out.println("Determinat of second matrix result:");
		determinantResult = matrixOperations.determinant(secondMatrix);
		System.out.println(determinantResult);
		System.out.println();
		
		System.out.println("Determinat of third matrix result:");
		determinantResult = matrixOperations.determinant(thirdMatrix);
		System.out.println(determinantResult);
		System.out.println();
		

	}

}
