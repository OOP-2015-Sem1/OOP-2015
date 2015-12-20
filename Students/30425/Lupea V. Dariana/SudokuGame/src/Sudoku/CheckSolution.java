package Sudoku;

import java.util.ArrayList;
import java.util.List;

public class CheckSolution extends UserGame {

	/* Returns whether user input is valid of given position. */
	public boolean isCheckValid(int x, int y) {
		return check[y][x];
	}

	/* Returns whether selected number is candidate at given position. */
	public boolean isSelectedNumberCandidate(int x, int y) {
		return game[y][x] == 0 && isPossibleX(game, y, selectedNumber) && isPossibleY(game, x, selectedNumber)
				&& isPossibleBlock(game, x, y, selectedNumber);
	}

	/* Checks whether given game is valid. */
	public boolean isValid(int[][] game) {
		return isValid(game, 0, new int[] { 0 });
	}

	/* Returns whether given number is candidate on x axis for given game. */

	private boolean isPossibleX(int[][] game, int y, int number) {
		for (int x = 0; x < 9; x++) {
			if (game[y][x] == number)
				return false;
		}
		return true;
	}

	/* Returns whether given number is candidate on y axis for given game. */

	private boolean isPossibleY(int[][] game, int x, int number) {
		for (int y = 0; y < 9; y++) {
			if (game[y][x] == number)
				return false;
		}
		return true;
	}

	/* Return the index of the block were the number is candidate */

	private int getBlockIndex(int i) {
		if (i < 3) {
			return 0;
		} else if (i < 6) {
			return 3;
		} else {
			return 6;
		}

	}
	/* Returns whether given number is candidate in block for given game. */

	private boolean isPossibleBlock(int[][] game, int x, int y, int number) {

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

	/*
	 * Returns next possible number from list for given position or -1 when list
	 * is empty.
	 */
	public int getNextPossibleNumber(int[][] game, int x, int y, List<Integer> numbers) {
		while (numbers.size() > 0) {
			int number = numbers.remove(0);// removes element an index 0
			if (isPossibleX(game, y, number) && isPossibleY(game, x, number) && isPossibleBlock(game, x, y, number))
				return number;
		}
		return -1;
	}

	/*
	 * Checks whether given game is valid, user should use the other isValid
	 * method. There may only be one solution.
	 */
	private boolean isValid(int[][] game, int index, int[] numberOfSolutions) {
		if (index > 80)
			return ++numberOfSolutions[0] == 1;// increase the number of
												// solutions

		int x = index % 9;
		int y = index / 9;

		if (game[y][x] == 0) {
			List<Integer> numbers = new ArrayList<Integer>();
			for (int i = 1; i <= 9; i++)
				numbers.add(i);

			while (numbers.size() > 0) {
				int number = getNextPossibleNumber(game, x, y, numbers);
				if (number == -1)
					break;
				game[y][x] = number;

				if (!isValid(game, index + 1, numberOfSolutions)) {
					game[y][x] = 0;
					return false;
				}
				game[y][x] = 0;
			}
		} else if (!isValid(game, index + 1, numberOfSolutions))
			return false;

		return true;
	}

}
