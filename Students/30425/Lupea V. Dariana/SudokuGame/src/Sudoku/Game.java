package Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {// to notify the player when changes have
										// been
										// made

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

	/* Returns whether given number is candidate in block for given game. */

	private boolean isPossibleBlock(int[][] game, int x, int y, int number) {

		int x_pos;
		int y_pos;

		if (x < 3) {
			x_pos = 0;
		} else if (x < 6) {
			x_pos = 3;
		} else {
			x_pos = 6;
		}

		if (y < 3) {
			y_pos = 0;
		} else if (y < 6) {
			y_pos = 3;
		} else {
			y_pos = 6;
		}

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
	private int getNextPossibleNumber(int[][] game, int x, int y, List<Integer> numbers) {
		while (numbers.size() > 0) {
			int number = numbers.remove(0);// removes element an index 0
			if (isPossibleX(game, y, number) && isPossibleY(game, x, number) && isPossibleBlock(game, x, y, number))
				return number;
		}
		return -1;
	}

	/* Generate Sudoku game from solution */
	private int[][] generateSolution(int[][] game, int index) {// recursive
																// method
		if (index > 80)// the board has 81 blocks =>solution
			return game;

		int x = index % 9;// find the x coordinate
		int y = index / 9;// find the y coordinate

		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++)
			numbers.add(i); // add the numbers in the array list
		Collections.shuffle(numbers);

		while (numbers.size() > 0) {
			int number = getNextPossibleNumber(game, x, y, numbers);
			if (number == -1)
				return null;

			game[y][x] = number;
			int[][] currentGame = generateSolution(game, index + 1);
			if (currentGame != null)
				return currentGame;
			game[y][x] = 0;
		}

		return null;
	}

}
