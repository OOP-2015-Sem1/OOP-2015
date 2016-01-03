package model;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Verification {
	public boolean ValidateInput(JFrame frame, int userInput, int row, int column, int[][] gameMatrix) {
		boolean isDigit = false;
		boolean inSquare = false;
		boolean onRow = false;
		boolean onColumn = false;
		ArrayList<Integer> answers = new ArrayList<Integer>();
		for (int i = 1; i < 10; i++) {
			answers.add(i);
		}
		Integer nb = new Integer(userInput);
		for (Integer value : answers) {
			if (value.equals(nb)) {
				isDigit = true;
			}
		}
		if (isDigit == false) {
			JOptionPane.showMessageDialog(frame.getParent(), "Please enter a digit not a word");
		} else {
			// Counting squares as: first line ->>column 1,2,3 etc//

			// First line //
			// First square //
			if ((row == 0 || row == 1 || row == 2) && (column == 0 || column == 1 || column == 2)) {
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}

			// Second square//
			if ((row == 0 || row == 1 || row == 2) && (column == 3 || column == 4 || column == 5)) {
				for (int i = 0; i < 3; i++)
					for (int j = 3; j < 6; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}

			// Third square //
			if ((row == 0 || row == 1 || row == 2) && (column == 6 || column == 7 || column == 8)) {
				for (int i = 0; i < 3; i++)
					for (int j = 6; j < 9; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}

			// Second line //
			// First Square //
			if ((row == 3 || row == 4 || row == 5) && (column == 0 || column == 1 || column == 2)) {
				for (int i = 3; i < 6; i++)
					for (int j = 0; j < 3; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}
			// Second Square //
			if ((row == 3 || row == 4 || row == 5) && (column == 3 || column == 4 || column == 5)) {
				for (int i = 3; i < 6; i++)
					for (int j = 3; j < 6; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}
			// Third Square //
			if ((row == 3 || row == 4 || row == 5) && (column == 6 || column == 7 || column == 8)) {
				for (int i = 3; i < 6; i++)
					for (int j = 6; j < 9; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}

			// Third line //
			// First square //
			if ((row == 6 || row == 7 || row == 8) && (column == 0 || column == 1 || column == 2)) {
				for (int i = 6; i < 9; i++)
					for (int j = 0; j < 3; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}

			// Second square //
			if ((row == 6 || row == 7 || row == 8) && (column == 3 || column == 4 || column == 5)) {
				for (int i = 6; i < 9; i++)
					for (int j = 3; j < 6; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}

			// Last square //
			if ((row == 6 || row == 7 || row == 8) && (column == 3 || column == 4 || column == 5)) {
				for (int i = 6; i < 9; i++)
					for (int j = 6; j < 9; j++) {
						if (userInput == gameMatrix[i][j]) {
							inSquare = true;
							break;
						}
					}
			}
			for (int i = 0; i < 9; i++) {
				if (gameMatrix[row][i] == userInput) {
					onColumn = true;
					break;
				}
			}
			for (int j = 0; j < 9; j++) {
				if (gameMatrix[j][column] == userInput) {
					onRow = true;
					break;
				}
			}
		}
		if (onColumn || onRow || inSquare) {
			System.out.println("Defeat");
			return false;
		} else {
			System.out.println("Victory");
			return true;
		}

	}
}
