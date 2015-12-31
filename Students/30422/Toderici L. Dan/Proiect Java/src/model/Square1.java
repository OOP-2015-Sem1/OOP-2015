package model;

public class Square1 extends AbstractSudokuSquare {
	public Square1() {
		sudokuGame = new int[3][3];
		sudokuGame[0][0] = 1;
		sudokuGame[0][1] = 4;
		sudokuGame[0][2] = 8;
		sudokuGame[1][0] = 2;
		sudokuGame[1][1] = 3;
		sudokuGame[1][2] = 6;
		sudokuGame[2][0] = 9;
		sudokuGame[2][1] = 5;
		sudokuGame[2][2] = 7;
	}
}
