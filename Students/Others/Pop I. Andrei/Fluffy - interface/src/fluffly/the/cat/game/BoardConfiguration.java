package fluffly.the.cat.game;

import java.awt.Point;

import fluffy.the.cat.io.BoardPrinter;
import fluffy.the.cat.models.Fluffy;

public class BoardConfiguration {
	public static final int MAX_VIEW_DISTANCE = 2;

	private static final int ROWS = 20;
	private static final int COLS = 40;
	private final char[][] board = new char[ROWS][COLS];
	private final Fluffy fluffy;

	public BoardConfiguration(Fluffy fluffy) {
		this.fluffy = fluffy;
	}

	public Point getInitialFluffyPosition() throws FluffyNotFoundException {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] == 'F') {
					return new Point(i, j);
				}
			}
		}
		throw new FluffyNotFoundException(
				"Fluffy is not initially on the board!");
	}

	public boolean isValidNewPosition(Point newFluffyPosition) {
		int x = newFluffyPosition.x;
		int y = newFluffyPosition.y;

		return (x >= 0 && x <= ROWS) && (y >= 0 && y <= COLS)
				&& board[x][y] != '*';
	}

	public void updateFluffyOnBoard(Point oldFluffyPosition,
			Point newFluffyPosition) {
		board[oldFluffyPosition.x][oldFluffyPosition.y] = ' ';
		board[newFluffyPosition.x][newFluffyPosition.y] = 'F';
	}

	public boolean fluffyReachedTheHat(Point newFluffyPosition) {
		return board[newFluffyPosition.x][newFluffyPosition.y] == 'W';
	}

	public void printBoard(BoardPrinter printer) {
		printer.print(this);
	}

	public char[][] getBoard() {
		return board;
	}

	public Fluffy getFluffy() {
		return fluffy;
	}

}
