//Harder Sudoku Game
public class Square2 {
	protected int[][] squareMatrix = new int[3][3];
	public Square2() {
		squareMatrix[0][0] = 1;
		squareMatrix[0][1] = 4;
		squareMatrix[0][2] = 8;
		squareMatrix[1][0] = 2;
		squareMatrix[1][1] = 3;
		squareMatrix[1][2] = 6;
		squareMatrix[2][0] = 9;
		squareMatrix[2][1] = 5;
		squareMatrix[2][2] = 7;
	}

	protected void displayMatrix() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				System.out.print(squareMatrix[i][j] + " ");
			System.out.println(" ");
		}
	}

	protected void permuteLines() {
		// Interchange line 0 with line 1
		for (int c = 0; c < 3; c++) {
			int aux = squareMatrix[0][c];
			squareMatrix[0][c] = squareMatrix[1][c];
			squareMatrix[1][c] = aux;
		}
		// Interchange line 1 with line 2
		for (int c = 0; c < 3; c++) {
			int aux = squareMatrix[1][c];
			squareMatrix[1][c] = squareMatrix[2][c];
			squareMatrix[2][c] = aux;
		}

	}

	protected void permuteColumns() {
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

	protected void permuteLinesNrOfTimes(int n) {
		for (int i = 0; i < n; i++) {
			this.permuteLines();
		}
	}

	protected int getElement(int lineIndex, int columnIndex) {
		return squareMatrix[lineIndex][columnIndex];
	}

	protected void permuteColumnsNrOfTimes(int n) {
		for (int i = 0; i < n; i++) {
			this.permuteColumns();
		}
	}

	public static void main(String[] args) {
		Square2 matrix = new Square2();
		matrix.displayMatrix();
		System.out.println("");
		matrix.permuteLinesNrOfTimes(2);
		matrix.displayMatrix();
		matrix.permuteColumnsNrOfTimes(2);
		System.out.println();
		matrix.displayMatrix();
	}
}
