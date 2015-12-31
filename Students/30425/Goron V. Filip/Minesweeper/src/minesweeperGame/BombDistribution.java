package minesweeperGame;

public class BombDistribution {
	static int bombsGiven = 0;
	static int bombsToGive = 99;
	static int freeSpacesLeft = 480;
	static int flaggedBombs = 99;
	static int[] bombVector = new int[99];
	static int firstPickCoordinates;

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
		for (int i = 0; i < bombsGiven; i++) {
			if (bombVector[i] == randomBomb || randomBomb == firstPickCoordinates) {
				randomBomb = myRandom();
			}
		}
		return randomBomb;
	}

	public static void bombInitialization(int x, int y) {
		bombVectorSetup();
		firstPickCoordinates = (y * 30) + x;
		int X, Y;
		int i, j;

		for (i = 0; i < 99; i++) {
			X = bombVector[i] % 30;
			Y = bombVector[i] / 30;
			System.out.println(X + "    " + Y);
			Main.field[X][Y].hasBomb = true;
		}
		for (j = 0; j < GamePlay.fieldDepth; j++) {
			for (i = 0; i < GamePlay.fieldLenght; i++) {
				Main.field[i][j].findNumberOfBombs(i, j);
			}
		}
	}
}
