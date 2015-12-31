package minesweeperGame;

import java.awt.Color;

public class Tiles {

	public boolean opened = false;
	public boolean hasBomb;
	public boolean canHaveBomb = true;
	public boolean flagged;
	public boolean maybe;
	public int bombsAround;
	public int closedAround;
	public int flaggedAround;
	private int tileX;
	private int tileY;

	public Tiles(int x, int y) {
		tileX = x;
		tileY = y;
	}

	public int getX() {
		return tileX;
	}

	public int getY() {
		return tileY;
	}

	public void block(int x, int y) {
		Main.field[x][y].canHaveBomb = true;
	}

	public void findNumberOfBombs(int i, int j) {
		int counter = 0;

		if (i < (GamePlay.fieldLenght - 1) && Main.field[i + 1][j].hasBomb == true) {
			counter++;
		}
		if (i > 0 && Main.field[i - 1][j].hasBomb == true) {
			counter++;
		}
		if (j < (GamePlay.fieldDepth - 1) && Main.field[i][j + 1].hasBomb == true) {
			counter++;
		}
		if (j > 0 && Main.field[i][j - 1].hasBomb == true) {
			counter++;
		}
		if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)
				&& Main.field[i + 1][j + 1].hasBomb == true) {
			counter++;
		}
		if (i < (GamePlay.fieldLenght - 1) && j > 0 && Main.field[i + 1][j - 1].hasBomb == true) {
			counter++;
		}
		if (i > 0 && j < (GamePlay.fieldDepth - 1) && Main.field[i - 1][j + 1].hasBomb == true) {
			counter++;
		}
		if (i > 0 && j > 0 && Main.field[i - 1][j - 1].hasBomb == true) {
			counter++;
		}
		bombsAround = counter;
	}

	public void findNumberOfClosed(int i, int j) {
		int counter = 0;

		if (i < (GamePlay.fieldLenght - 1) && Main.field[i + 1][j].opened == false) {
			counter++;
		}
		if (i > 0 && Main.field[i - 1][j].opened == false) {
			counter++;
		}
		if (j < (GamePlay.fieldDepth - 1) && Main.field[i][j + 1].opened == false) {
			counter++;
		}
		if (j > 0 && Main.field[i][j - 1].opened == false) {
			counter++;
		}
		if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)
				&& Main.field[i + 1][j + 1].opened == false) {
			counter++;
		}
		if (i < (GamePlay.fieldLenght - 1) && j > 0 && Main.field[i + 1][j - 1].opened == false) {
			counter++;
		}
		if (i > 0 && j < (GamePlay.fieldDepth - 1) && Main.field[i - 1][j + 1].opened == false) {
			counter++;
		}
		if (i > 0 && j > 0 && Main.field[i - 1][j - 1].opened == false) {
			counter++;
		}
		closedAround = counter;
	}

	public void findNumberOfFlagged(int i, int j) {
		int counter = 0;

		if (i < (GamePlay.fieldLenght - 1) && Main.field[i + 1][j].flagged == true) {
			counter++;
		}
		if (i > 0 && Main.field[i - 1][j].flagged == true) {
			counter++;
		}
		if (j < (GamePlay.fieldDepth - 1) && Main.field[i][j + 1].flagged == true) {
			counter++;
		}
		if (j > 0 && Main.field[i][j - 1].flagged == true) {
			counter++;
		}
		if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)
				&& Main.field[i + 1][j + 1].flagged == true) {
			counter++;
		}
		if (i < (GamePlay.fieldLenght - 1) && j > 0 && Main.field[i + 1][j - 1].flagged == true) {
			counter++;
		}
		if (i > 0 && j < (GamePlay.fieldDepth - 1) && Main.field[i - 1][j + 1].flagged == true) {
			counter++;
		}
		if (i > 0 && j > 0 && Main.field[i - 1][j - 1].flagged == true) {
			counter++;
		}
		bombsAround = counter;
	}

	public char charOutput() {
		char out = 0;
		if (opened == false) {
			if (flagged == true) {
				out = 'F';

			} else {
				out = 'C';
			}
		}

		if (opened == true) {
			if (hasBomb == true) {
				out = 'X';

			} else {
				if (bombsAround == 0) {
					out = ' ';
				} else {
					out = (char) ('0' + bombsAround);
				}

			}
		}

		return out;
	}

	public String output() {
		String out = null;
		if (opened == false) {
			if (flagged == true) {
				out = "F";
			} else {
				out = " ";
			}
		}

		if (opened == true) {
			if (hasBomb == true) {
				out = "X";
				GamePlay.endGame(false);
			} else {
				if (bombsAround == 0) {
					out = " ";
				} else {
					out = Integer.toString(bombsAround);
				}
			}
		}

		return out;
	}

	public void open(int i, int j) {

		if (flagged == false && opened == false) {
			if (hasBomb == true) {
				Graphics.tileButton[i][j].setBackground(Color.RED);
				GamePlay.endGame(false);
			}
			opened = true;

			if (bombsAround == 0) {
				openAround(i, j);
			}
			Graphics.tileButton[i][j].setText(output());
			Graphics.tileButton[i][j].setBackground(Color.GREEN);
			Auxiliary.tilesOpened++;
			Auxiliary.checkForWin();
		}
	}

	public void openAround(int i, int j) {
		if (Main.field[i][j].hasBomb == false) {
			if (i < (GamePlay.fieldLenght - 1)) {
				Main.field[i + 1][j].open(i + 1, j);
			}
			if (i > 0) {
				Main.field[i - 1][j].open(i - 1, j);
			}
			if (j < (GamePlay.fieldDepth - 1)) {
				Main.field[i][j + 1].open(i, j + 1);
			}
			if (j > 0) {
				Main.field[i][j - 1].open(i, j - 1);
			}
			if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)) {
				Main.field[i + 1][j + 1].open(i + 1, j + 1);
			}
			if (i < (GamePlay.fieldLenght - 1) && j > 0) {
				Main.field[i + 1][j - 1].open(i + 1, j - 1);
			}
			if (i > 0 && j < (GamePlay.fieldDepth - 1)) {
				Main.field[i - 1][j + 1].open(i - 1, j + 1);
			}
			if (i > 0 && j > 0) {
				Main.field[i - 1][j - 1].open(i - 1, j - 1);
			}
		}
	}

	public void flag(int i, int j) {

		if (opened == false) {
			flagged = !flagged;
			Graphics.tileButton[i][j].setText(output());
		}
	}

	public void flagAround(int i, int j) {
		if (i < (GamePlay.fieldLenght - 1)) {
			Main.field[i + 1][j].flag(i, j);
		}
		if (i > 0) {
			Main.field[i - 1][j].flag(i, j);
		}
		if (j < (GamePlay.fieldDepth - 1)) {
			Main.field[i][j + 1].flag(i, j);
		}
		if (j > 0) {
			Main.field[i][j - 1].flag(i, j);
		}
		if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)) {
			Main.field[i + 1][j + 1].flag(i, j);
		}
		if (i < (GamePlay.fieldLenght - 1) && j > 0) {
			Main.field[i + 1][j - 1].flag(i, j);
		}
		if (i > 0 && j < (GamePlay.fieldDepth - 1)) {
			Main.field[i - 1][j + 1].flag(i, j);
		}
		if (i > 0 && j > 0) {
			Main.field[i - 1][j - 1].flag(i, j);
		}
	}
}
