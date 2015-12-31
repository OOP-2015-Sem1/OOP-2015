package model;

public class Square5 extends AbstractSudokuSquare {

	public Square5() {
		sudokuGame = new int[3][3];
		sudokuGame[0][0] = 4;
		sudokuGame[0][1] = 1;
		sudokuGame[0][2] = 5;
		sudokuGame[1][0] = 6;
		sudokuGame[1][1] = 7;
		sudokuGame[1][2] = 3;
		sudokuGame[2][0] = 2;
		sudokuGame[2][1] = 9;
		sudokuGame[2][2] = 8;
	}

}
