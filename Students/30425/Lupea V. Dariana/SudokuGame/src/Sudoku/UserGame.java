package Sudoku;

public class UserGame {
	public boolean[][] check; // Holder for checking validity of game.
	public int selectedNumber; // Selected number by user.
	public int[][] solution; // Generated solution.
	public int[][] game; // Generated game with user input.

	/*
	 * Checks user input against the solution and puts it into a check matrix.
	 */
	public void checkGame() {
		selectedNumber = 0;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++)
				check[y][x] = game[y][x] == solution[y][x];//
		}
	}

	/* Sets selected number to user input. */
	public void setSelectedNumber(int selectedNumber) {
		this.selectedNumber = selectedNumber; // number selected by user
	}

	/* Return number selected by user */
	public int getSelectedNumber() {
		return selectedNumber;
	}

	/* Sets given number on given position in the game. */
	public void setNumber(int x, int y, int number) {
		game[y][x] = number;
	}

	/* Returns number of given position. */
	public int getNumber(int x, int y) {
		return game[y][x];
	}

}
