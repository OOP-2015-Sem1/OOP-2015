package minesweeperAI;

import minesweeperGame.Main;

public class Auxx {

	public static void checkIdMaybeint(int i, int j) {
		int counter = 0;
	
		
		for (int k = 0; k < 8; k++) {
			
			if (i < 29 && Main.aiField[i + 1][j].hasBomb == true) {
				counter++;
			}
			if (i > 0 && Main.aiField[i - 1][j].hasBomb == true) {
				counter++;
			}
			if (j < 15 && Main.aiField[i][j + 1].hasBomb == true) {
				counter++;
			}
			if (j > 0 && Main.aiField[i][j - 1].hasBomb == true) {
				counter++;
			}
			if (i < 29 && j < 15 && Main.aiField[i + 1][j + 1].hasBomb == true) {
				counter++;
			}
			if (i < 29 && j > 0 && Main.aiField[i + 1][j - 1].hasBomb == true) {
				counter++;
			}
			if (i > 0 && j < 15 && Main.aiField[i - 1][j + 1].hasBomb == true) {
				counter++;
			}
			if (i > 0 && j > 0 && Main.aiField[i - 1][j - 1].hasBomb == true) {
				counter++;
			}
		}
	}

}
