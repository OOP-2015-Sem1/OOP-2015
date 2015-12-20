package Sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateSolution extends CheckSolution {

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
		Collections.shuffle(numbers);//shuffle in order to avoid obtaining the same solution

		while (numbers.size() > 0) {
			int number = getNextPossibleNumber(game, x, y, numbers);
			if (number == -1)
				return null;

			game[y][x] = number;//obtained a correct number
			int[][] currentGame = generateSolution(game, index + 1);
			if (currentGame != null)
				return currentGame;
			game[y][x] = 0;//make the field blank
		}

		return null;
	}

}
