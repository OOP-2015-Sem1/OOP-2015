package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {

	private int[][] solution; // Generated solution.
	public static int[][] game; // Generated game with user input.
	private boolean[][] check; // Holder for checking validity of game.
	private int selectedNumber; // Selected number by user.
	private boolean help; // Help turned on or off.

	/* Constructor */
	public Game() {
		newGame();
		check = new boolean[9][9];
		help = true;
	}

	/*
	 * Generates a new Sudoku game. All observers will be notified, update
	 * action: new game.
	 */
	public void newGame() {
		solution = generateSolution(new int[9][9], 0);
		game = generateGame(copy(solution));
		setChanged();// marks this Observable object as having been changed
		notifyObservers(UpdateAction.NEW_GAME);// each Observer has its update
												// method
	}

	/*
	 * Checks user input against the solution and puts it into a check matrix.
	 */
	public void checkGame() {
		selectedNumber = 0;
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++)
				check[y][x] = game[y][x] == solution[y][x];
		}
		setChanged();
		notifyObservers(UpdateAction.CHECK);
	}

	/*
	 * Sets help turned on or off. All observers will be notified, update
	 * action: help.
	 */
	public void setHelp(boolean help) {
		this.help = help;
		setChanged();
		notifyObservers(UpdateAction.HELP);
	}

	/* Sets selected number to user input. */
	public void setSelectedNumber(int selectedNumber) {
		this.selectedNumber = selectedNumber;
		setChanged();
		notifyObservers(UpdateAction.NEW_GAME);
	}

	/* Returns number selected by user. */
	public int getSelectedNumber() {
		return selectedNumber;
	}

	/* Returns whether help is turned on or off. */
	public boolean isHelp() {
		return help;
	}

	/* Returns whether selected number is candidate at given position. */
	public boolean isSelectedNumberCandidate(int x, int y) {
		return game[y][x] == 0 && isPossibleX(game, y, selectedNumber) && isPossibleY(game, x, selectedNumber)
				&& isPossibleBlock(game, x, y, selectedNumber);
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

	/*
	 * Returns next possible number from list for given position or -1 when list
	 * is empty.
	 */
	public int getNextPossibleNumber(int[][] game, int x, int y, List<Integer> numbers) {
		while (numbers.size() > 0) {
			int number = numbers.remove(0);// removes element an index 0 and
											// puts it in a variable
			if (isPossibleX(game, y, number) && isPossibleY(game, x, number) && isPossibleBlock(game, x, y, number))
				return number;
		}
		return -1;
	}

	/* Generate Sudoku game solution */
	public int[][] generateSolution(int[][] game, int index) {// recursive
																// method

		if (index > 80)// the board has 81 blocks =>solution
			return game;

		int x = index % 9;// find the x coordinate
		int y = index / 9;// find the y coordinate

		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 1; i <= 9; i++)
			numbers.add(i); // add all the possible numbers in the array list
		Collections.shuffle(numbers);// shuffle in order to avoid obtaining the
										// same solution

		while (numbers.size() > 0) {
			int number = getNextPossibleNumber(game, x, y, numbers);
			if (number == -1)
				return null;

			game[y][x] = number;// obtained a correct number
			int[][] currentGame = generateSolution(game, index + 1);
			if (currentGame != null)
				return currentGame;
			game[y][x] = 0;// make the field blank
		}

		return null;
	}

	/* Generates Sudoku game from solution. */
	public int[][] generateGame(int[][] game) {
		List<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < 81; i++)
			positions.add(i);// all possible indexes are added to an array->
								// positions
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
			int position = positions.remove(0);// variable positions stores each
												// index
			int x = position % 9;// find x and y coordinates from index
			int y = position / 9;
			int value = game[y][x];// find the value located at that index
			game[y][x] = 0;// the value at that position is made 0 (blank)

			if (!isValid(game)) // if the game does not remain valid => put
								// value back
				game[y][x] = value;
		}

		return game;
	}

	/* Checks whether given game is valid. */
	public boolean isValid(int[][] game) {
		return isValid(game, 0, new int[] { 0 });
	}

	/*
	 * Checks whether given game is valid, user should use the other isValid
	 * method. There may only be one solution.
	 */

	private boolean isValid(int[][] game, int index, int[] numberOfSolutions) {
		if (index > 80)// if solutions is found
			return ++numberOfSolutions[0] == 1;// increase the number of
												// solutions - true/false

		int x = index % 9;
		int y = index / 9;

		if (game[y][x] == 0) {// blank
			List<Integer> numbers = new ArrayList<Integer>();
			for (int i = 1; i <= 9; i++)
				numbers.add(i);

			while (numbers.size() > 0) {
				int number = getNextPossibleNumber(game, x, y, numbers);
				if (number == -1)// the value fits in => return true
					break;
				game[y][x] = number;

				if (!isValid(game, index + 1, numberOfSolutions)) {
					game[y][x] = 0;// if game does not remain valid, make the
									// field blank back
					return false;
				}
				game[y][x] = 0;
			}
		} else if (!isValid(game, index + 1, numberOfSolutions))
			return false;

		return true;
	}

	/* Copies a game. */
	private int[][] copy(int[][] game) {
		int[][] copy = new int[9][9];
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++)
				copy[y][x] = game[y][x];
		}
		return copy;
	}
}
