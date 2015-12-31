package minesweeperAI;

import minesweeperGame.Main;

public class AI {
	public static int x;
	public static int y;

	public static int[] randomClick() {
		double a = Math.random();
		double b = Math.random();
		int[] click = new int[3];

		a = (int) (29 * a);
		b = (int) (15 * b);

		x = (int) (a);
		y = (int) (b);

		click[0] = x;
		click[1] = y;
		click[2] = 'o';
		return click;
	}

	public static int[] click() {
		int[] click = new int[2];

		click[0] = x;
		click[1] = y;

		return click;
	}

	public static void decisions() {
		int i, j;

		for (j = 0; j < 16; j++) {
			for (i = 0; i < 30; i++) {
				if (Main.field[i][j].opened == true) {
					int ca, fa, ba;

					Main.field[i][j].findNumberOfFlagged(i, j);
					Main.field[i][j].findNumberOfClosed(i, j);

					ca = Main.field[i][j].closedAround;
					fa = Main.field[i][j].flaggedAround;
					ba = Main.field[i][j].bombsAround;

					if (ba == ca) {
						Main.field[i][j].flagAround(i, j);
					}

					if (ba == fa) {
						Main.field[i][j].openAround(i, j);
					}
				}
			}
		}
	}
}
