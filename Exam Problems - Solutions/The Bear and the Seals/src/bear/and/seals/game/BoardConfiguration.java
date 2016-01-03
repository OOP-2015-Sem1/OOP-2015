package bear.and.seals.game;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import bear.and.seals.io.BoardPrinter;
import bear.and.seals.models.Entity;
import bear.and.seals.models.Hole;

public class BoardConfiguration {
	private static final int ROWS = 10;
	private static final int COLS = 20;

	private final char[][] board = new char[ROWS][COLS];

	public void updateBoard(List<Entity> entities) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				board[i][j] = '.';
			}
		}
		for (Entity e : entities) {
			int x = e.getPosition().x;
			int y = e.getPosition().y;
			if (board[x][y] != 'B') {
				board[x][y] = e.getBoardRepresentation();
			}
		}
	}

	public char[][] getBoard() {
		return this.board;
	}

	public void printBoard(BoardPrinter printer) {
		printer.print(this);
	}

	public Point getRandomlyUnnoccupiedPosition() {
		Random r = new Random();
		int x = r.nextInt(ROWS);
		int y = r.nextInt(COLS);
		while (board[x][y] != '.') {
			x = r.nextInt(ROWS);
			y = r.nextInt(COLS);
		}

		return new Point(x, y);
	}

	public Point getRandomHoleLocation(List<Hole> holes) {
		int holeIndex = new Random().nextInt(holes.size());
		return new Point(holes.get(holeIndex).getPosition());
	}

}
