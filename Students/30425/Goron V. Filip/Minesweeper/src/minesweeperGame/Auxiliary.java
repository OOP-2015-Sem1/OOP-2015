package minesweeperGame;

public class Auxiliary {
	static int tilesOpened = 0;

	public static boolean chanceToBlock() {
		boolean block;
		double a = Math.random();

		if (a > 0.65) {
			block = false;
		} else {
			block = true;
		}

		return block;
	}

	public static void blockTiles(int i, int j) {
		Graphics.field[i][j].block(i, j);
		if (i < (GamePlay.fieldLenght - 1)) {
			Graphics.field[i + 1][j].block(i + 1, j);
		}
		if (i > 0) {
			Graphics.field[i - 1][j].block(i - 1, j);
		}
		if (j < (GamePlay.fieldDepth - 1)) {
			Graphics.field[i][j + 1].block(i, j + 1);
		}
		if (j > 0) {
			Graphics.field[i][j - 1].block(i, j - 1);
		}
		if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)) {
			Graphics.field[i + 1][j + 1].block(i + 1, j + 1);
		}
		if (i < (GamePlay.fieldLenght - 1) && j > 0) {
			Graphics.field[i + 1][j - 1].block(i + 1, j - 1);
		}
		if (i > 0 && j < (GamePlay.fieldDepth - 1)) {
			Graphics.field[i - 1][j + 1].block(i - 1, j + 1);
		}
		if (i > 0 && j > 0) {
			Graphics.field[i - 1][j - 1].block(i - 1, j + 1);
		}
	}

	public static void printField() {
		int i, j;
		System.out.print("");
		for (j = 0; j < GamePlay.fieldLenght; j++) {
			if (j < 10) {
				System.out.print("    " + j);
			} else {
				System.out.print("   " + j);
			}
		}
		System.out.println();
		for (j = 0; j < GamePlay.fieldDepth; j++) {

			if (j < 10) {
				System.out.print(" " + j + "  ");
			} else {
				System.out.print(j + "  ");
			}

			for (i = 0; i < GamePlay.fieldLenght; i++) {
				System.out.print(Graphics.field[i][j].charOutput() + "    ");

			}
			System.out.println();
			System.out.println();
		}
	}

	public static boolean checkForWin() {
		boolean won = false;
		if(tilesOpened == 381){
			won = true;
		}
		if(won == true){
			GamePlay.endGame(won);
		}
		return won;
	}
}
