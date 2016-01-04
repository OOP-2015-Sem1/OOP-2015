package minesweeperGame;

import java.util.Scanner;

import minesweeperHelp.Help;

public class GamePlay {
	public static int fieldLenght = 30;
	public static int fieldDepth = 16;
	public static boolean gameOver = false;
	public static boolean firstPick = true;
	public static int inputX;
	public static int inputY;
	public static boolean firstRound = true;
	static Graphics Window;

	public static void endGame(boolean won) {
		gameOver = true;
		Graphics.endGamePopUp(won);
		System.exit(0);
	}

	public static void playGame() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        char decision;
		Graphics.instructionsPopUp();
		
		new Help();
		new Graphics();

		inputX = Graphics.getCoordinates() % 15;
		inputY = Graphics.getCoordinates() / 15;

		Auxiliary.blockTiles(inputX, inputY);
		BombDistribution.bombInitialization();

		Auxiliary.printField();

		while (gameOver == false) {

			inputX = Graphics.getCoordinates() % 15;
			inputY = Graphics.getCoordinates() / 15;

			Auxiliary.printField();

			decision = input.next().charAt(0);
			
			
			
			if (decision == 'o' || decision == 'O') {
				Graphics.field[inputX][inputY].open();
			} else if (decision == 'f' || decision == 'o') {
				Graphics.field[inputX][inputY].flag();
			}
			if (Auxiliary.checkForWin() == true) {
				endGame(true);
			}
		}
	}
}
