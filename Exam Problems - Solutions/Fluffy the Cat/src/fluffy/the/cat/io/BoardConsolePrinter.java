package fluffy.the.cat.io;

import fluffly.the.cat.game.BoardConfiguration;
import static fluffly.the.cat.game.BoardConfiguration.MAX_VIEW_DISTANCE;

public class BoardConsolePrinter implements BoardPrinter {

	@Override
	public void print(BoardConfiguration boardConfiguration) {
		char[][] boardData = boardConfiguration.getBoard();
		int rows = boardData.length;
		int cols = boardData[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (withinRangeOfFluffy(i, j, boardConfiguration)) {
					System.out.printf("%c ", boardData[i][j]);
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}

	}

	private boolean withinRangeOfFluffy(int i, int j,
			BoardConfiguration boardConfiguration) {
		int fluffyX = boardConfiguration.getFluffy().getPosition().x;
		int fluffyY = boardConfiguration.getFluffy().getPosition().y;

		double dist = Math.sqrt(Math.pow(fluffyX - i, 2)
				+ Math.pow(fluffyY - j, 2));

		return dist < MAX_VIEW_DISTANCE;
	}
}
