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
