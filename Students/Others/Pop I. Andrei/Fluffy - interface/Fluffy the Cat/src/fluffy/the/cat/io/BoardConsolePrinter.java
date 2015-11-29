package fluffy.the.cat.io;


import fluffly.the.cat.game.BoardConfiguration;


public class BoardConsolePrinter extends BoardPrinterClass {
	
	@Override
	public void printConsole(BoardConfiguration boardConfiguration) {
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
	
}
