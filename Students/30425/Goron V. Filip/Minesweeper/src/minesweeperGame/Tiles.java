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
		Graphics.field[x][y].canHaveBomb = true;
	}

	public int findNumberOfBombs() {
		int counter = 0;

		if (tileX < (GamePlay.fieldLenght - 1) && Graphics.field[tileX + 1][tileY].hasBomb == true) {
			counter++;
		}
		if (tileX > 0 && Graphics.field[tileX - 1][tileY].hasBomb == true) {
			counter++;
		}
		if (tileY < (GamePlay.fieldDepth - 1) && Graphics.field[tileX][tileY + 1].hasBomb == true) {
			counter++;
		}
		if (tileY > 0 && Graphics.field[tileX][tileY - 1].hasBomb == true) {
			counter++;
		}
		if (tileX < (GamePlay.fieldLenght - 1) && tileY < (GamePlay.fieldDepth - 1)
				&& Graphics.field[tileX + 1][tileY + 1].hasBomb == true) {
			counter++;
		}
		if (tileX < (GamePlay.fieldLenght - 1) && tileY > 0 && Graphics.field[tileX + 1][tileY - 1].hasBomb == true) {
			counter++;
		}
		if (tileX > 0 && tileY < (GamePlay.fieldDepth - 1) && Graphics.field[tileX - 1][tileY + 1].hasBomb == true) {
			counter++;
		}
		if (tileX > 0 && tileY > 0 && Graphics.field[tileX - 1][tileY - 1].hasBomb == true) {
			counter++;
		}
		bombsAround = counter;
		return counter;
	}

	public int findNumberOfClosed() {
		int counter = 0;

		if (tileX < (GamePlay.fieldLenght - 1) && Graphics.field[tileX + 1][tileY].opened == false) {
			counter++;
		}
		if (tileX > 0 && Graphics.field[tileX - 1][tileY].opened == false) {
			counter++;
		}
		if (tileY < (GamePlay.fieldDepth - 1) && Graphics.field[tileX][tileY + 1].opened == false) {
			counter++;
		}
		if (tileY > 0 && Graphics.field[tileX][tileY - 1].opened == false) {
			counter++;
		}
		if (tileX < (GamePlay.fieldLenght - 1) && tileY < (GamePlay.fieldDepth - 1)
				&& Graphics.field[tileX + 1][tileY + 1].opened == false) {
			counter++;
		}
		if (tileX < (GamePlay.fieldLenght - 1) && tileY > 0 && Graphics.field[tileX + 1][tileY - 1].opened == false) {
			counter++;
		}
		if (tileX > 0 && tileY < (GamePlay.fieldDepth - 1) && Graphics.field[tileX - 1][tileY + 1].opened == false) {
			counter++;
		}
		if (tileX > 0 && tileY > 0 && Graphics.field[tileX - 1][tileY - 1].opened == false) {
			counter++;
		}
		closedAround = counter;
		return counter;
	}

	public int findNumberOfFlagged() {
		int counter = 0;

		if (tileX < (GamePlay.fieldLenght - 1) && Graphics.field[tileX + 1][tileY].flagged == true) {
			counter++;
		}
		if (tileX > 0 && Graphics.field[tileX - 1][tileY].flagged == true) {
			counter++;
		}
		if (tileY < (GamePlay.fieldDepth - 1) && Graphics.field[tileX][tileY + 1].flagged == true) {
			counter++;
		}
		if (tileY > 0 && Graphics.field[tileX][tileY - 1].flagged == true) {
			counter++;
		}
		if (tileX < (GamePlay.fieldLenght - 1) && tileY < (GamePlay.fieldDepth - 1)
				&& Graphics.field[tileX + 1][tileY + 1].flagged == true) {
			counter++;
		}
		if (tileX < (GamePlay.fieldLenght - 1) && tileY > 0 && Graphics.field[tileX + 1][tileY - 1].flagged == true) {
			counter++;
		}
		if (tileX > 0 && tileY < (GamePlay.fieldDepth - 1) && Graphics.field[tileX - 1][tileY + 1].flagged == true) {
			counter++;
		}
		if (tileX > 0 && tileY > 0 && Graphics.field[tileX - 1][tileY - 1].flagged == true) {
			counter++;
		}
		bombsAround = counter;
		return counter;
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

	public void open() {

		if (flagged == false && opened == false) {
			if (hasBomb == true) {
				Graphics.tileButton[tileX][tileY].setBackground(Color.RED);
				GamePlay.endGame(false);
			}
			opened = true;

			if (bombsAround == 0) {
				openAround(tileX, tileY);
			}
			Graphics.tileButton[tileX][tileY].setText(output());
			Graphics.tileButton[tileX][tileY].setBackground(Color.GREEN);
			Auxiliary.tilesOpened++;
			Auxiliary.checkForWin();
		}
	}

	public void openAround(int i, int j) {
		if (Graphics.field[i][j].hasBomb == false) {
			if (i < (GamePlay.fieldLenght - 1)) {
				Graphics.field[i + 1][j].open();
			}
			if (i > 0) {
				Graphics.field[i - 1][j].open();
			}
			if (j < (GamePlay.fieldDepth - 1)) {
				Graphics.field[i][j + 1].open();
			}
			if (j > 0) {
				Graphics.field[i][j - 1].open();
			}
			if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)) {
				Graphics.field[i + 1][j + 1].open();
			}
			if (i < (GamePlay.fieldLenght - 1) && j > 0) {
				Graphics.field[i + 1][j - 1].open();
			}
			if (i > 0 && j < (GamePlay.fieldDepth - 1)) {
				Graphics.field[i - 1][j + 1].open();
			}
			if (i > 0 && j > 0) {
				Graphics.field[i - 1][j - 1].open();
			}
		}
	}

	public void flag() {

		if (opened == false) {
			flagged = !flagged;
			Graphics.tileButton[tileX][tileY].setText(output());
			Graphics.tileButton[tileX][tileY].setBackground(Color.YELLOW);
		}
	}

	public void helpFlagAround(int i, int j) {
		if (i < (GamePlay.fieldLenght - 1) && Graphics.field[i + 1][j].flagged == false) {
			Graphics.field[i + 1][j].flag();
		}
		if (i > 0 && Graphics.field[i - 1][j].flagged == false) {
			Graphics.field[i - 1][j].flag();
		}
		if (j < (GamePlay.fieldDepth - 1) && Graphics.field[i][j + 1].flagged == false) {
			Graphics.field[i][j + 1].flag();
		}
		if (j > 0 && Graphics.field[i][j - 1].flagged == false) {
			Graphics.field[i][j - 1].flag();
		}
		if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)
				&& Graphics.field[i + 1][j + 1].flagged == false) {
			Graphics.field[i + 1][j + 1].flag();
		}
		if (i < (GamePlay.fieldLenght - 1) && j > 0 && Graphics.field[i + 1][j - 1].flagged == false) {
			Graphics.field[i + 1][j - 1].flag();
		}
		if (i > 0 && j < (GamePlay.fieldDepth - 1) && Graphics.field[i - 1][j + 1].flagged == false) {
			Graphics.field[i - 1][j + 1].flag();
		}
		if (i > 0 && j > 0 && Graphics.field[i - 1][j - 1].flagged == false) {
			Graphics.field[i - 1][j - 1].flag();
		}
	}

	public void flagAround(int i, int j) {
		if (i < (GamePlay.fieldLenght - 1)) {
			Graphics.field[i + 1][j].flag();
		}
		if (i > 0) {
			Graphics.field[i - 1][j].flag();
		}
		if (j < (GamePlay.fieldDepth - 1)) {
			Graphics.field[i][j + 1].flag();
		}
		if (j > 0) {
			Graphics.field[i][j - 1].flag();
		}
		if (i < (GamePlay.fieldLenght - 1) && j < (GamePlay.fieldDepth - 1)) {
			Graphics.field[i + 1][j + 1].flag();
		}
		if (i < (GamePlay.fieldLenght - 1) && j > 0) {
			Graphics.field[i + 1][j - 1].flag();
		}
		if (i > 0 && j < (GamePlay.fieldDepth - 1)) {
			Graphics.field[i - 1][j + 1].flag();
		}
		if (i > 0 && j > 0) {
			Graphics.field[i - 1][j - 1].flag();
		}
	}
}
