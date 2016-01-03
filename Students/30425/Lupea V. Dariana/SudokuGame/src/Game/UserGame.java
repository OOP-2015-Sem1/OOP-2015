package Game;

public class UserGame implements GameInterface {
	private int[][] solution; // Generated solution.
	private int[][] game; // Generated game with user input.
	private boolean[][] check; // Holder for checking validity of game.
	private int selectedNumber; // Selected number by user.

	/*
	 * Checks user input against the solution and puts it into a check matrix.
	 */
	public void checkGame() {
		selectedNumber = 0;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++)
				check[y][x] = game[y][x] == solution[y][x];
		}
	}

	/* Sets selected number to user input. */
	public void setSelectedNumber(int selectedNumber) {
		this.selectedNumber = selectedNumber;
	}

	/* Returns number selected by user. */
	public int getSelectedNumber() {
		return selectedNumber;
	}

	/* Returns whether selected number is candidate at given position. */
	public boolean isSelectedNumberCandidate(int x, int y) {
		return game[y][x] == 0 && isPossibleX(game, y, selectedNumber) && isPossibleY(game, x, selectedNumber)
				&& isPossibleBlock(game, x, y, selectedNumber);
	}

	/* Returns whether given number is candidate on x axis for given game. */

	public boolean isPossibleX(int[][] game, int y, int number) {
		for (int x = 0; x < 9; x++) {
			if (game[y][x] == number)
				return false;
		}
		return true;
	}

	/* Returns whether given number is candidate on y axis for given game. */

	public boolean isPossibleY(int[][] game, int x, int number) {
		for (int y = 0; y < 9; y++) {
			if (game[y][x] == number)
				return false;
		}
		return true;
	}

	/* Return the index of the block were the number is candidate */

	public int getBlockIndex(int i) {
		if (i < 3) {
			return 0;
		} else if (i < 6) {
			return 3;
		} else {
			return 6;
		}

	}
	/* Returns whether given number is candidate in block for given game. */

	public boolean isPossibleBlock(int[][] game, int x, int y, int number) {

		int x_pos = getBlockIndex(x);
		int y_pos = getBlockIndex(y);

		for (int yy = y_pos; yy < y_pos + 3; yy++) {
			for (int xx = x_pos; xx < x_pos + 3; xx++) {
				if (game[yy][xx] == number)
					return false;
			}
		}
		return true;
	}

	/* Sets given number on given position in the game. */
	public void setNumber(int x, int y, int number) {
		game[y][x] = number;
	}

	/* Returns number of given position. */
	public int getNumber(int x, int y) {
		return game[y][x];
	}

	/* Returns whether user input is valid of given position. */
	public boolean isCheckValid(int x, int y) {
		return check[y][x];
	}
}
