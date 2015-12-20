package Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameGenerator extends GenerateSolution {

	/* Generates Sudoku game from solution. */
	public int[][] generateGame(int[][] game) {
		List<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < 81; i++)
			positions.add(i);
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
			int value = game[y][x];
			game[y][x] = 0;

			if (!isValid(game))
				game[y][x] = value;
		}

		return game;
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
