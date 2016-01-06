package minesweeperGame;

import minesweeperHelp.Help;

public class GamePlay {
	public static final int FIELD_LENGTH = 30;
	public static final int FIELD_DEPTH = 16;
	public static boolean gameOver = false;



	public static void endGame(boolean won) {
		Graphics.endGamePopUp(won);
		System.exit(0);
		
	}


	public static void playGame() {

		Graphics.instructionsPopUp();
		new Graphics();
		new Help();

		BombDistribution.bombInitialization();

		while (!gameOver) {

			if (Auxiliary.checkForWin()) {
				endGame(true);
			}
		}
	}
}
