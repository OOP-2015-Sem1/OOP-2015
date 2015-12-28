package model;

public class MatrixOperation {
	protected void permuteLines(int[][] squareMatrix) {
		// Interchange line 0 with line 1 //
		for (int c = 0; c < 3; c++) {
			int aux = squareMatrix[0][c];
			squareMatrix[0][c] = squareMatrix[1][c];
			squareMatrix[1][c] = aux;
		}
		// Interchange line 1 with line 2 //
		for (int c = 0; c < 3; c++) {
			int aux = squareMatrix[1][c];
			squareMatrix[1][c] = squareMatrix[2][c];
			squareMatrix[2][c] = aux;
		}

	}

	protected void permuteColumns(int[][] squareMatrix) {
		// Interchange column 0 with column 1
		for (int c = 0; c < 3; c++) {
			int aux = squareMatrix[c][0];
			squareMatrix[c][0] = squareMatrix[c][1];
			squareMatrix[c][1] = aux;
		}
		// Interchange column 1 with column 2
		for (int c = 0; c < 3; c++) {
			int aux = squareMatrix[c][1];
			squareMatrix[c][1] = squareMatrix[c][2];
			squareMatrix[c][2] = aux;
		}
	}

	protected void permuteLinesNrOfTimes(int[][] squareMatrix, int n) {
		for (int i = 0; i < n; i++) {
			permuteLines(squareMatrix);
		}
	}

	protected int getElement(int[][] squareMatrix, int lineIndex, int columnIndex) {
		return squareMatrix[lineIndex][columnIndex];
	}

	protected void permuteColumnsNrOfTimes(int[][] squareMatrix, int n) {
		for (int i = 0; i < n; i++) {
			permuteColumns(squareMatrix);
		}
	}

	protected void displayMatrix(int[][] squareMatrix) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				System.out.print(squareMatrix[i][j] + " ");
			System.out.println(" ");
		}
	}

}
