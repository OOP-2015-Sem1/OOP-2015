package model;
import java.util.Random;

public class SudokuMatrixGenerator {
	private AbstractSudokuSquare[][] firstAuxiliary = new Square1[3][3];
	private AbstractSudokuSquare[][] secondAuxiliary = new Square2[3][3];
	private AbstractSudokuSquare[][] thirdAuxiliary = new Square3[3][3];
	private AbstractSudokuSquare[][] fourthAuxiliary = new Square4[3][3];
	private AbstractSudokuSquare[][] fifthAuxiliary = new Square5[3][3];
	private int[][] Sudoku1 = new int[9][9];
	private int[][] Sudoku2 = new int[9][9];
	private int[][] Sudoku3 = new int[9][9];
	private int[][] Sudoku4 = new int[9][9];
	private int[][] Sudoku5 = new int[9][9];

	public SudokuMatrixGenerator() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				firstAuxiliary[i][j] = new Square1();
				secondAuxiliary[i][j] = new Square2();
				thirdAuxiliary[i][j] = new Square3();
				fourthAuxiliary[i][j] = new Square4();
				fifthAuxiliary[i][j] = new Square5();
			}

		// Permute them for a number of times//
		MatrixOperation matrixOperations = new MatrixOperation();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				matrixOperations.permuteLinesNrOfTimes(firstAuxiliary[i][j].returnSmallSquare(), i + 2 * j);
				matrixOperations.permuteColumnsNrOfTimes(firstAuxiliary[i][j].returnSmallSquare(), j + i + 2 * j);
				matrixOperations.permuteLinesNrOfTimes(secondAuxiliary[i][j].returnSmallSquare(), i + 2 * j);
				matrixOperations.permuteColumnsNrOfTimes(secondAuxiliary[i][j].returnSmallSquare(), j + i + 2 * j);
				matrixOperations.permuteLinesNrOfTimes(thirdAuxiliary[i][j].returnSmallSquare(), i + 2 * j);
				matrixOperations.permuteColumnsNrOfTimes(thirdAuxiliary[i][j].returnSmallSquare(), j + i + 2 * j);
				matrixOperations.permuteLinesNrOfTimes(fourthAuxiliary[i][j].returnSmallSquare(), i + 2 * j);
				matrixOperations.permuteColumnsNrOfTimes(fourthAuxiliary[i][j].returnSmallSquare(), j + i + 2 * j);
				matrixOperations.permuteLinesNrOfTimes(fifthAuxiliary[i][j].returnSmallSquare(), i + 2 * j);
				matrixOperations.permuteColumnsNrOfTimes(fifthAuxiliary[i][j].returnSmallSquare(), j + i + 2 * j);
			}

		// De facto translation into a 9*9 sudoku game matrix//
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				int indl = 3 * i;
				int indc = 3 * j;
				for (int dl = 0; dl < 3; dl++)
					for (int dc = 0; dc < 3; dc++) {

						Sudoku1[indl + dl][indc + dc] = matrixOperations
								.getElement(firstAuxiliary[i][j].returnSmallSquare(), dl, dc);
						Sudoku2[indl + dl][indc + dc] = matrixOperations
								.getElement(secondAuxiliary[i][j].returnSmallSquare(), dl, dc);
						Sudoku3[indl + dl][indc + dc] = matrixOperations
								.getElement(thirdAuxiliary[i][j].returnSmallSquare(), dl, dc);
						Sudoku4[indl + dl][indc + dc] = matrixOperations
								.getElement(fourthAuxiliary[i][j].returnSmallSquare(), dl, dc);
						Sudoku5[indl + dl][indc + dc] = matrixOperations
								.getElement(fifthAuxiliary[i][j].returnSmallSquare(), dl, dc);

					}
			}
	}

	public int[][] returnSudokuMatrix() {
		Random r = new Random();
		int number = r.nextInt(5);
		switch (number) {
		case 0:
			return Sudoku1;
		case 1:
			return Sudoku2;
		case 2:
			return Sudoku3;
		case 3:
			return Sudoku4;
		case 4:
			return Sudoku5;
		default:
			return Sudoku1;
		}
	}

	public static void main(String[] args) {
		new SudokuMatrixGenerator();
	}
}
