package fluffy.the.cat.io;

import fluffly.the.cat.game.BoardConfiguration;

import java.io.IOException;

import javax.swing.JFrame;

public class BoardConsolePrinter extends AbstractGameBoard {
	
	@Override
	public void print(BoardConfiguration boardConfiguration) {
		/*try {
			Runtime.getRuntime().exec("cls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.out.print("\033[H\033[2J");  
	    //System.out.flush();

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
