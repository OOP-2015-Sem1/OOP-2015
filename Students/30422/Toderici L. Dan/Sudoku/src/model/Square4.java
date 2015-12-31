package model;

public class Square4 extends AbstractSudokuSquare{

	public Square4() {
		sudokuGame = new int[3][3];
		sudokuGame[0][0] = 5;
		sudokuGame[0][1] = 1;
		sudokuGame[0][2] = 4;
		sudokuGame[1][0] = 8;
		sudokuGame[1][1] = 2;
		sudokuGame[1][2] = 6;
		sudokuGame[2][0] = 7;
		sudokuGame[2][1] = 9;
		sudokuGame[2][2] = 3;
	}
	
}
