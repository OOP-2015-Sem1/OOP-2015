package game;

import methods.constants.auxiliary.Constants;

public class BoardConfiguration {

	private int[][] board;

	public int[][] getBoard() {
		return board;
	}

	public void modifyPositionOnBoard(int i, int j, int value) {
		this.board[i][j] = value;
	}

	public BoardConfiguration(int rows, int cols) {
		board = new int[rows + 1][cols + 1];
		board[rows / 2 - 1][cols / 2 - 1] = Constants.WHITE;
		board[rows / 2][cols / 2] = Constants.WHITE;
		board[rows / 2 - 1][cols / 2] = Constants.BLACK;
		board[rows / 2][cols / 2 - 1] = Constants.BLACK;
	}
}
