package fluffly.the.cat.game;

import java.awt.Point;

import fluffly.the.cat.game.views.GamePanel;
import fluffy.the.cat.io.AbstractGameBoard;
import fluffy.the.cat.io.BoardPrinter;
import fluffy.the.cat.models.Fluffy;

public class BoardConfiguration {

	private final Fluffy fluffy;

	public BoardConfiguration(Fluffy fluffy) {
		this.fluffy = fluffy;
	}

	public Point getInitialFluffyPosition() throws FluffyNotFoundException {
		for (int i = 0; i <Constants.ROWS; i++) {
			for (int j = 0; j < Constants.COLS; j++) {
				if (Constants.board[i][j] == 'F') {
					return new Point(i, j);
				}
			}
		}
		throw new FluffyNotFoundException("Fluffy is not initially on the board!");
	}

	public boolean isValidNewPosition(Point newFluffyPosition) {
		int x = newFluffyPosition.x;
		int y = newFluffyPosition.y;

		return (x >= 0 && x <= Constants.ROWS) && (y >= 0 && y <= Constants.COLS) && Constants.board[x][y] != '*';
	}

	public void updateFluffyOnBoard(Point oldFluffyPosition, Point newFluffyPosition) {
		Constants.board[oldFluffyPosition.x][oldFluffyPosition.y] = ' ';
		Constants.board[newFluffyPosition.x][newFluffyPosition.y] = 'F';
	}

	public boolean fluffyReachedTheHat(Point newFluffyPosition) {
		return Constants.board[newFluffyPosition.x][newFluffyPosition.y] == 'W';
	}

	public char[][] getBoard() {
		return Constants.board;
	}

	public Fluffy getFluffy() {
		return fluffy;
	}

	public void printBoard(AbstractGameBoard printer) {
		printer.print(this);
	}

}
