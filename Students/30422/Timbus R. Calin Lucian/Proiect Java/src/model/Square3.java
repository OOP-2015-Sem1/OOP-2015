package model;

public class Square3 extends AbstractSudokuSquare{
	
	public Square3() {
		sudokuGame = new int[3][3];
		sudokuGame[0][0] = 6;
		sudokuGame[0][1] = 1;
		sudokuGame[0][2] = 3;
		sudokuGame[1][0] = 2;
		sudokuGame[1][1] = 4;
		sudokuGame[1][2] = 9;
		sudokuGame[2][0] = 8;
		sudokuGame[2][1] = 5;
		sudokuGame[2][2] = 7;
	}
	
}
