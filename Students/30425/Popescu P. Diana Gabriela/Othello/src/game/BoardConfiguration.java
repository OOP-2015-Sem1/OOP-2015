package game;

public class BoardConfiguration {

	public static final int ROWS = 8;
	public static final int COLS = 8;

	public static final int GREEN = 0;
	public static final int WHITE = 1;
	public static final int BLACK = 2;
	public static final int GAME_OVER=5;

	private int[][] board;

	public int[][] getBoard() {
		return board;
	}

	public void modifyPositionOnBoard(int i, int j, int value) {
		this.board[i][j] = value;
	}

	public BoardConfiguration() {
		board = new int[ROWS+1][COLS+1];
		board[3][3] = WHITE;
		board[4][4] = WHITE;
		board[3][4] = BLACK;
		board[4][3] = BLACK;
	}
}
