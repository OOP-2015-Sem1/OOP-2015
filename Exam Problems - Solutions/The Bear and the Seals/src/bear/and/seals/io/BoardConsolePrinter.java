package bear.and.seals.io;

import bear.and.seals.game.BoardConfiguration;

public class BoardConsolePrinter implements BoardPrinter {

	@Override
	public void print(BoardConfiguration boardConfiguration) {
		char[][] boardData = boardConfiguration.getBoard();
		int rows = boardData.length;
		int cols = boardData[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.printf("%c ", boardData[i][j]);
			}
			System.out.println();
		}

	}

}
