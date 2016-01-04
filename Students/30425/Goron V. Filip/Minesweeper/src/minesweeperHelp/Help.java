package minesweeperHelp;

import minesweeperGame.GamePlay;
import minesweeperGame.Graphics;

public class Help {
	public static boolean[][] tileHelped = new boolean[GamePlay.fieldLenght][GamePlay.fieldDepth];

	public Help() {
		int i, j;

		for (j = 0; j < 16; j++) {
			for (i = 0; i < 30; i++) {
				tileHelped[i][j] = false;
			}
		}

	}

	public static void nextHelp() {
		int i, j;

		for (j = 0; j < 16; j++) {
			for (i = 0; i < 30; i++) {
				if (Graphics.field[i][j].opened == true && tileHelped[i][j] == false) {

					Graphics.field[i][j].findNumberOfFlagged();
					Graphics.field[i][j].findNumberOfClosed();

					System.out.println(Graphics.field[i][j].findNumberOfBombs() + " " + Graphics.field[i][j].flaggedAround);

					if (Graphics.field[i][j].findNumberOfBombs() == Graphics.field[i][j].findNumberOfClosed()) {
						Graphics.field[i][j].helpFlagAround(i, j);
						tileHelped[i][j] = true;
						break;
					}

					if (Graphics.field[i][j].bombsAround == Graphics.field[i][j].findNumberOfFlagged()) {
						Graphics.field[i][j].openAround(i, j);
						tileHelped[i][j] = true;
						break;
					}

				}
			}
		}
	}

}
