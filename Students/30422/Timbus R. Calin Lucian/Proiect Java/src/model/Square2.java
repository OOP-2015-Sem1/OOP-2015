package model;

public class Square2 extends AbstractSudokuSquare {
	public Square2() {
		sudokuGame = new int[3][3];
		sudokuGame[0][0] = 1;
		sudokuGame[0][1] = 2;
		sudokuGame[0][2] = 3;
		sudokuGame[1][0] = 4;
		sudokuGame[1][1] = 5;
		sudokuGame[1][2] = 6;
		sudokuGame[2][0] = 7;
		sudokuGame[2][1] = 8;
		sudokuGame[2][2] = 9;
	}

}
