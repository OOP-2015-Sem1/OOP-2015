package minesweeperGame;

public class BombDistribution {
	static int bombsGiven = 0;
	static int bombsToGive = 99;
	static int freeSpacesLeft = 480;
	static int flaggedBombs = 100;
	static int[] bombVector = new int[99];

	public static void bombVectorSetup() {
		for (int i = 0; i < 99; i++) {
			bombVector[i] = myRandom();
			bombsGiven++;
			bombsToGive--;
			freeSpacesLeft--;
		}
	}

	public static int myRandom() {
		double randomNumber = Math.random();

		int randomBomb = (int) (480 * randomNumber);
		for (int i = 0; i < 99; i++) {
			if (bombVector[i] == randomBomb || !Graphics.field[i % 30][i / 30].canHaveBomb) {
				randomBomb = myRandom();
			}
		}
		return randomBomb;
	}

	public static void bombInitialization() {
		int i, j;
		
		bombVectorSetup();

		for (i = 0; i < 99; i++) {
			Graphics.field[bombVector[i] % 30][bombVector[i] / 30].hasBomb = true;
		}
		for (j = 0; j < GamePlay.FIELD_DEPTH; j++) {
			for (i = 0; i < GamePlay.FIELD_LENGTH; i++) {
				Graphics.field[i][j].findNumberOfBombs();
			}
		}
	}
}
