package Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateGame implements GameInterface {

	/* Returns whether given number is candidate on x axis for given game. */
	public boolean isPossibleX(int[][] game, int y, int number) {
		for (int x = 0; x < 9; x++) {
			if (game[y][x] == number)
				return false;
		}
		return true;
	}

	/* Returns whether given number is candidate on x axis for given game. */
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

	/*
	 * Returns next possible number from list for given position or -1 when list
	 * is empty.
	 */
	private int getNextPossibleNumber(int[][] game, int x, int y, List<Integer> numbers) {
		while (numbers.size() > 0) {
			int number = numbers.remove(0);// return element removed from list
			if (isPossibleX(game, y, number) && isPossibleY(game, x, number) && isPossibleBlock(game, x, y, number))
				return number;
		}
		return -1;
	}

	/* Generates Sudoku game solution. */
	private int[][] generateSolution(int[][] game, int index) {// recursive
																// method
		if (index > 80)// a solution is found
			return game;// return the solution

		int x = index % 9;
		int y = index / 9;

		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++)
			numbers.add(i);// possible numbers are added to an array
		Collections.shuffle(numbers);// shuffle to avoid obtaining the same
										// solution

		while (numbers.size() > 0) {
			int number = getNextPossibleNumber(game, x, y, numbers);// one
																	// integer
																	// from
																	// [1..9]
			if (number == -1)
				return null;

			game[y][x] = number;// found number is placed on the current
								// location
			int[][] currentGame = generateSolution(game, index + 1);
			if (currentGame != null)
				return currentGame;
			game[y][x] = 0;// current location put back to 0 -> blank
		}

		return null;
	}

	/* Generates Sudoku game from solution. */
	private int[][] generateGame(int[][] game) {
		List<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < 81; i++)
			positions.add(i);// fill the list with all possible positions
		Collections.shuffle(positions);
		return generateGame(game, positions);
	}

	/*
	 * Generates Sudoku game from solution, user should use the other
	 * generateGame method. This method simple removes a number at a position.
	 * If the game isn't anymore valid after this action, the game will be
	 * brought back to previous state.
	 */
	private int[][] generateGame(int[][] game, List<Integer> positions) {
		while (positions.size() > 0) {
			int position = positions.remove(0);
			int x = position % 9;
			int y = position / 9;
			int value = game[y][x];// store the position in value
			game[y][x] = 0;// make a blank there

			if (!isValid(game))// if game does not remain valid after deleting
								// value => put value back
				game[y][x] = value;
		}

		return game;
	}

	/* Checks whether given game is valid. */
	private boolean isValid(int[][] game) {
		return isValid(game, 0, new int[] { 0 });// pass by reference
	}

	/*
	 * Checks whether given game is valid, user should use the other isValid
	 * method. There may only be one solution.
	 */
	private boolean isValid(int[][] game, int index, int[] numberOfSolutions) {
		if (index > 80)
			return ++numberOfSolutions[0] == 1;// true if 1 or false if>1(only 1
												// solution accepted)

		int x = index % 9;
		int y = index / 9;

		if (game[y][x] == 0) // blank field
		{
			List<Integer> numbers = new ArrayList<Integer>();
			for (int i = 1; i <= 9; i++)
				numbers.add(i);// fill a list with numbers 1 to 9

			while (numbers.size() > 0) {
				int number = getNextPossibleNumber(game, x, y, numbers);
				if (number == -1)
					break;// and return true
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
