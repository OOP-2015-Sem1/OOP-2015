package controller;

import gui.Game;

// I have set some conventions for these strange numbers. At that time they made sense. 
// 0 - is for empty space in the matrix
// 11 for red square
// 12 for lShape and so on until 14
// 111-114 are for pieces that can no longer move, they've settled down
// 200 for the edges of the game

public class Move {

	public static void init() { // REWORKED
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				if (i == 9) {
					Main.matrice[i][j] = 200;
				} else {
					Main.matrice[i][j] = 0;
				}
				if (j == 0 || j == 12 || j == 1 || j == 2 || j == 13 || j == 14) {
					Main.matrice[i][j] = 200;
				}

			}
		}
		Game.repaint();
	}

	public static void afis() {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				System.out.print(Main.matrice[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}

	public static boolean stopCond() { // REWORKED
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (Main.matrice[Main.row + i][Main.col + j] < 20 && Main.matrice[Main.row + i][Main.col + j] > 10) {
					if (Main.matrice[Main.row + i][Main.col + j] + Main.matrice[Main.row + i + 1][Main.col + j] > 30) {
						return true;
					}
				}
			}
		return false;
	}

	public static void moveLine(int l1, int c1) { // REWORKED
		if (Main.matrice[l1][c1] > 10 && Main.matrice[l1][c1] < 20) {
			Main.matrice[l1 + 1][c1] = Main.matrice[l1][c1];
			Main.matrice[l1][c1] = 0;
		}
		if (Main.matrice[l1][c1 + 1] > 10 && Main.matrice[l1][c1 + 1] < 20) {
			Main.matrice[l1 + 1][c1 + 1] = Main.matrice[l1][c1 + 1];
			Main.matrice[l1][c1 + 1] = 0;
		}
		if (Main.matrice[l1][c1 + 2] > 10 && Main.matrice[l1][c1 + 2] < 20) {
			Main.matrice[l1 + 1][c1 + 2] = Main.matrice[l1][c1 + 2];
			Main.matrice[l1][c1 + 2] = 0;
		}
	}

	public static void moveColRight(int l1, int c1) { // REWORKED
		if (Main.matrice[l1][c1] > 10 && Main.matrice[l1][c1] < 100) {
			Main.matrice[l1][c1 + 1] = Main.matrice[l1][c1];
			Main.matrice[l1][c1] = 0;
		}
		if (Main.matrice[l1 + 1][c1] > 10 && Main.matrice[l1 + 1][c1] < 100) {
			Main.matrice[l1 + 1][c1 + 1] = Main.matrice[l1 + 1][c1];
			Main.matrice[l1 + 1][c1] = 0;
		}
		if (Main.matrice[l1 + 2][c1] > 10 && Main.matrice[l1 + 2][c1] < 100) {
			Main.matrice[l1 + 2][c1 + 1] = Main.matrice[l1 + 2][c1];
			Main.matrice[l1 + 2][c1] = 0;
		}

	}

	public static void moveColLeft(int l1, int c1) { // REWORKED
		if (Main.matrice[l1][c1] > 10 && Main.matrice[l1][c1] < 100) {
			Main.matrice[l1][c1 - 1] = Main.matrice[l1][c1];
			Main.matrice[l1][c1] = 0;
		}
		if (Main.matrice[l1 + 1][c1] > 10 && Main.matrice[l1 + 1][c1] < 100) {
			Main.matrice[l1 + 1][c1 - 1] = Main.matrice[l1 + 1][c1];
			Main.matrice[l1 + 1][c1] = 0;
		}
		if (Main.matrice[l1 + 2][c1] > 10 && Main.matrice[l1 + 2][c1] < 100) {
			Main.matrice[l1 + 2][c1 - 1] = Main.matrice[l1 + 2][c1];
			Main.matrice[l1 + 2][c1] = 0;
		}

	}

	public static void clearLine(int l1, int c1) { // REWORKED
		if (Main.matrice[l1][c1] < 100) {
			Main.matrice[l1][c1] = 0;
		}
		if (Main.matrice[l1][c1] < 100) {
			Main.matrice[l1][c1 + 1] = 0;
		}
		if (Main.matrice[l1][c1 + 2] < 100) {
			Main.matrice[l1][c1 + 2] = 0;
		}
	}

	public static void clearCol(int l1, int c1) { // REWORKED
		if (Main.matrice[l1][c1] < 100) {
			Main.matrice[l1][c1] = 0;
		}
		if (Main.matrice[l1 + 1][c1] < 100) {
			Main.matrice[l1 + 1][c1] = 0;
		}
		if (Main.matrice[l1 + 2][c1] < 100) {
			Main.matrice[l1 + 2][c1] = 0;
		}
	}

	public static boolean moveDown() { // REWORKED
		if (stopCond()) {
			stabilize();
			checkLine();
			Main.stoped = true;
			Game.repaint();
			return false;
		} else {
			moveLine(Main.row + 2, Main.col);
			moveLine(Main.row + 1, Main.col);
			moveLine(Main.row, Main.col);
			clearLine(Main.row, Main.col);
			adjust();
			Main.row++;
			Game.repaint();
			return true;
		}
	}

	public static boolean moveRightCond(int line, int col) { // REWORKED
		for (int i = line; i < line + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if (Main.matrice[i][j] < 20 && Main.matrice[i][j] > 10) {
					if (Main.matrice[i][j] + Main.matrice[i][j + 1] > 120) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean moveLeftCond(int line, int col) { // REWORKED
		for (int i = line; i < line + 3; i++) {
			for (int j = col; j < col + 3; j++) {
				if (Main.matrice[i][j] > 10 && Main.matrice[i][j] < 100) {
					if (Main.matrice[i][j] + Main.matrice[i][j - 1] > 120) {
						return false;
					}
				}

			}
		}
		return true;
	}

	public static void moveRight() { // REWORKED
		if (moveRightCond(Main.row, Main.col)) {
			moveColRight(Main.row, Main.col + 2);
			moveColRight(Main.row, Main.col + 1);
			moveColRight(Main.row, Main.col);
			clearCol(Main.row, Main.col);
			adjust();
			Main.col++;
			Game.repaint();
		}
	}

	public static void moveLeft() { // REWORKED
		if (moveLeftCond(Main.row, Main.col)) {
			moveColLeft(Main.row, Main.col);
			moveColLeft(Main.row, Main.col + 1);
			moveColLeft(Main.row, Main.col + 2);
			clearCol(Main.row, Main.col + 2);
			adjust();
			Main.col--;
			Game.repaint();
		}
	}

	public static void stabilize() { // REWORKED
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				if (Main.matrice[i][j] > 10 && Main.matrice[i][j] < 20) {
					Main.matrice[i][j] = Main.matrice[i][j] + 100;
				}
			}
		}
		Game.repaint();
	}

	public static void elimLine(int line) { // ALREADY GOOD
		for (int i = line; i > 0; i--) {
			for (int j = 3; j < 12; j++) {
				Main.matrice[i][j] = Main.matrice[i - 1][j];
			}
		}
		for (int i = 3; i < 12; i++) {
			Main.matrice[0][i] = 0;
		}
		Main.score = Main.score + 5;
		Game.repaint();
	}

	public static boolean checkLine() { // REWORKED
		boolean full;
		for (int i = 0; i < 9; i++) {
			full = true;
			for (int j = 3; j < 12; j++) {
				if (Main.matrice[i][j] == 0)
					full = false;
			}
			if (full)
				elimLine(i);
		}
		return true;
	}

	public static void adjust() { // REWORKED
		for (int i = 0; i < 10; i++) {
			Main.matrice[i][2] = 200;
			Main.matrice[i][12] = 200;
		}
	}

	public static boolean shiftLineShapeCondition() {
		if (Main.matrice[Main.row + 1][Main.col] == 14) {
			if (Main.matrice[Main.row][Main.col + 1] == 0 && Main.matrice[Main.row][Main.col + 2] == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (Main.matrice[Main.row + 1][Main.col] == 0 && Main.matrice[Main.row + 2][Main.col] == 0) {
				return true;
			} else
				return false;
		}
	}

	public static void shiftLineShape() {
		if (Main.matrice[Main.row + 1][Main.col] > 10 && Main.matrice[Main.row + 1][Main.col] < 100) {
			Main.matrice[Main.row][Main.col + 1] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row][Main.col + 2] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 1][Main.col] = 0;
			Main.matrice[Main.row + 2][Main.col] = 0;
		} else {
			Main.matrice[Main.row + 1][Main.col] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 2][Main.col] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row][Main.col + 1] = 0;
			Main.matrice[Main.row][Main.col + 2] = 0;
		}
	}

	public static boolean shiftZShapeCondition() {
		if (Main.matrice[Main.row + 1][Main.col] == 13) {
			if (Main.matrice[Main.row][Main.col + 1] == 0 && Main.matrice[Main.row + 1][Main.col + 2] == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (Main.matrice[Main.row + 1][Main.col] == 0 && Main.matrice[Main.row + 2][Main.col + 1] == 0) {
				return true;
			} else
				return false;
		}
	}

	public static void shiftZShape() {
		if (Main.matrice[Main.row + 1][Main.col] > 10 && Main.matrice[Main.row + 1][Main.col] < 100) {
			Main.matrice[Main.row][Main.col + 1] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 1][Main.col + 2] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 1][Main.col] = 0;
			Main.matrice[Main.row + 2][Main.col + 1] = 0;
		} else {
			Main.matrice[Main.row + 1][Main.col] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 2][Main.col + 1] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row][Main.col + 1] = 0;
			Main.matrice[Main.row + 1][Main.col + 2] = 0;
		}
	}

	public static boolean shiftLShapeCondition() {
		if (Main.matrice[Main.row + 1][Main.col] == 12) {
			if (Main.matrice[Main.row][Main.col + 1] == 0 && Main.matrice[Main.row][Main.col + 2] == 0
					&& Main.matrice[Main.row + 1][Main.col + 2] == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (Main.matrice[Main.row + 1][Main.col] == 0 && Main.matrice[Main.row + 2][Main.col] == 0
					&& Main.matrice[Main.row + 2][Main.col + 1] == 0) {
				return true;
			} else
				return false;
		}
	}

	public static void shiftLShape() {
		if (Main.matrice[Main.row + 1][Main.col] > 10 && Main.matrice[Main.row + 1][Main.col] < 100) {
			Main.matrice[Main.row][Main.col + 1] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row][Main.col + 2] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 1][Main.col + 2] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 1][Main.col] = 0;
			Main.matrice[Main.row + 2][Main.col] = 0;
			Main.matrice[Main.row + 2][Main.col + 1] = 0;
		} else {
			Main.matrice[Main.row + 1][Main.col] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 2][Main.col] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row + 2][Main.col + 1] = Main.matrice[Main.row][Main.col];
			Main.matrice[Main.row][Main.col + 1] = 0;
			Main.matrice[Main.row][Main.col + 2] = 0;
			Main.matrice[Main.row + 1][Main.col + 2] = 0;
		}
	}

	public static void shift() {
		int shapeForm = Main.matrice[Main.row][Main.col];
		switch (shapeForm) {
		case 11:
			break;

		case 12:
			if (shiftLShapeCondition()) {
				shiftLShape();
			}
			break;
		case 13:
			if (shiftZShapeCondition()) {
				shiftZShape();
			}
			break;
		case 14:
			if (shiftLineShapeCondition()) {
				shiftLineShape();
			}
			break;
		}
		Game.repaint();
	}
}
