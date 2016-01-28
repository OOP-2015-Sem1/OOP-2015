package minesweeperGame;

import java.awt.Color;

public class Tiles {

	public boolean opened = false;
	public boolean hasBomb;
	public boolean canHaveBomb = true;
	public boolean flagged;
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

		if (tileX < (GamePlay.FIELD_LENGTH - 1) && Graphics.field[tileX + 1][tileY].hasBomb) {
			counter++;
		}
		if (tileX > 0 && Graphics.field[tileX - 1][tileY].hasBomb) {
			counter++;
		}
		if (tileY < (GamePlay.FIELD_DEPTH - 1) && Graphics.field[tileX][tileY + 1].hasBomb) {
			counter++;
		}
		if (tileY > 0 && Graphics.field[tileX][tileY - 1].hasBomb) {
			counter++;
		}
		if (tileX < (GamePlay.FIELD_LENGTH - 1) && tileY < (GamePlay.FIELD_DEPTH - 1)
				&& Graphics.field[tileX + 1][tileY + 1].hasBomb) {
			counter++;
		}
		if (tileX < (GamePlay.FIELD_LENGTH - 1) && tileY > 0 && Graphics.field[tileX + 1][tileY - 1].hasBomb) {
			counter++;
		}
		if (tileX > 0 && tileY < (GamePlay.FIELD_DEPTH - 1) && Graphics.field[tileX - 1][tileY + 1].hasBomb) {
			counter++;
		}
		if (tileX > 0 && tileY > 0 && Graphics.field[tileX - 1][tileY - 1].hasBomb) {
			counter++;
		}
		bombsAround = counter;
		return counter;
	}

	public int findNumberOfClosed() {
		int counter = 0;

		if (tileX < (GamePlay.FIELD_LENGTH - 1) && !Graphics.field[tileX + 1][tileY].opened) {
			counter++;
		}
		if (tileX > 0 && !Graphics.field[tileX - 1][tileY].opened) {
			counter++;
		}
		if (tileY < (GamePlay.FIELD_DEPTH - 1) && !Graphics.field[tileX][tileY + 1].opened) {
			counter++;
		}
		if (tileY > 0 && !Graphics.field[tileX][tileY - 1].opened) {
			counter++;
		}
		if (tileX < (GamePlay.FIELD_LENGTH - 1) && tileY < (GamePlay.FIELD_DEPTH - 1)
				&& !Graphics.field[tileX + 1][tileY + 1].opened) {
			counter++;
		}
		if (tileX < (GamePlay.FIELD_LENGTH - 1) && tileY > 0 && !Graphics.field[tileX + 1][tileY - 1].opened) {
			counter++;
		}
		if (tileX > 0 && tileY < (GamePlay.FIELD_DEPTH - 1) && !Graphics.field[tileX - 1][tileY + 1].opened) {
			counter++;
		}
		if (tileX > 0 && tileY > 0 && !Graphics.field[tileX - 1][tileY - 1].opened) {
			counter++;
		}
		closedAround = counter;
		return counter;
	}

	public int findNumberOfFlagged() {
		int counter = 0;

		if (tileX < (GamePlay.FIELD_LENGTH - 1) && Graphics.field[tileX + 1][tileY].flagged) {
			counter++;
		}
		if (tileX > 0 && Graphics.field[tileX - 1][tileY].flagged) {
			counter++;
		}
		if (tileY < (GamePlay.FIELD_DEPTH - 1) && Graphics.field[tileX][tileY + 1].flagged) {
			counter++;
		}
		if (tileY > 0 && Graphics.field[tileX][tileY - 1].flagged) {
			counter++;
		}
		if (tileX < (GamePlay.FIELD_LENGTH - 1) && tileY < (GamePlay.FIELD_DEPTH - 1)
				&& Graphics.field[tileX + 1][tileY + 1].flagged) {
			counter++;
		}
		if (tileX < (GamePlay.FIELD_LENGTH - 1) && tileY > 0 && Graphics.field[tileX + 1][tileY - 1].flagged) {
			counter++;
		}
		if (tileX > 0 && tileY < (GamePlay.FIELD_DEPTH - 1) && Graphics.field[tileX - 1][tileY + 1].flagged) {
			counter++;
		}
		if (tileX > 0 && tileY > 0 && Graphics.field[tileX - 1][tileY - 1].flagged) {
			counter++;
		}
		bombsAround = counter;
		return counter;
	}

	public char charOutput() {
		char out = 0;
		if (!opened) {
			if (flagged) {
				out = 'F';

			} else {
				out = 'C';
			}
		}

		if (opened) {
			if (hasBomb) {
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
		if (!opened) {
			if (flagged) {
				out = "F";
			} else {
				out = " ";
			}
		}

		if (opened) {
			if (hasBomb) {
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

		if (!flagged && !opened) {
			if (hasBomb) {
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
		if (!Graphics.field[i][j].hasBomb) {
			if (i < (GamePlay.FIELD_LENGTH - 1)) {
				Graphics.field[i + 1][j].open();
			}
			if (i > 0) {
				Graphics.field[i - 1][j].open();
			}
			if (j < (GamePlay.FIELD_DEPTH - 1)) {
				Graphics.field[i][j + 1].open();
			}
			if (j > 0) {
				Graphics.field[i][j - 1].open();
			}
			if (i < (GamePlay.FIELD_LENGTH - 1) && j < (GamePlay.FIELD_DEPTH - 1)) {
				Graphics.field[i + 1][j + 1].open();
			}
			if (i < (GamePlay.FIELD_LENGTH - 1) && j > 0) {
				Graphics.field[i + 1][j - 1].open();
			}
			if (i > 0 && j < (GamePlay.FIELD_DEPTH - 1)) {
				Graphics.field[i - 1][j + 1].open();
			}
			if (i > 0 && j > 0) {
				Graphics.field[i - 1][j - 1].open();
			}
		}
	}

	public void flag() {

		if (!opened) {
			flagged = !flagged;
			if (flagged) {
				Graphics.tileButton[tileX][tileY].setBackground(Color.YELLOW);
				BombDistribution.flaggedBombs--;
				Graphics.refreshBombCounter(BombDistribution.flaggedBombs);

			} else {
				Graphics.tileButton[tileX][tileY].setBackground(null);
				BombDistribution.flaggedBombs++;
				Graphics.refreshBombCounter(BombDistribution.flaggedBombs);

			}
			Graphics.tileButton[tileX][tileY].setText(output());
			Graphics.tileButton[tileX][tileY].setBackground(Color.YELLOW);
		}
	}

	public void helpFlagAround(int i, int j) {
		if (i < (GamePlay.FIELD_LENGTH - 1) && !Graphics.field[i + 1][j].flagged) {
			Graphics.field[i + 1][j].flag();
		}
		if (i > 0 && !Graphics.field[i - 1][j].flagged) {
			Graphics.field[i - 1][j].flag();
		}
		if (j < (GamePlay.FIELD_DEPTH - 1) && !Graphics.field[i][j + 1].flagged) {
			Graphics.field[i][j + 1].flag();
		}
		if (j > 0 && !Graphics.field[i][j - 1].flagged) {
			Graphics.field[i][j - 1].flag();
		}
		if (i < (GamePlay.FIELD_LENGTH - 1) && j < (GamePlay.FIELD_DEPTH - 1)
				&& !Graphics.field[i + 1][j + 1].flagged) {
			Graphics.field[i + 1][j + 1].flag();
		}
		if (i < (GamePlay.FIELD_LENGTH - 1) && j > 0 && !Graphics.field[i + 1][j - 1].flagged) {
			Graphics.field[i + 1][j - 1].flag();
		}
		if (i > 0 && j < (GamePlay.FIELD_DEPTH - 1) && !Graphics.field[i - 1][j + 1].flagged) {
			Graphics.field[i - 1][j + 1].flag();
		}
		if (i > 0 && j > 0 && !Graphics.field[i - 1][j - 1].flagged) {
			Graphics.field[i - 1][j - 1].flag();
		}
	}

	public void flagAround(int i, int j) {
		if (i < (GamePlay.FIELD_LENGTH - 1)) {
			Graphics.field[i + 1][j].flag();
		}
		if (i > 0) {
			Graphics.field[i - 1][j].flag();
		}
		if (j < (GamePlay.FIELD_DEPTH - 1)) {
			Graphics.field[i][j + 1].flag();
		}
		if (j > 0) {
			Graphics.field[i][j - 1].flag();
		}
		if (i < (GamePlay.FIELD_LENGTH - 1) && j < (GamePlay.FIELD_DEPTH - 1)) {
			Graphics.field[i + 1][j + 1].flag();
		}
		if (i < (GamePlay.FIELD_LENGTH - 1) && j > 0) {
			Graphics.field[i + 1][j - 1].flag();
		}
		if (i > 0 && j < (GamePlay.FIELD_DEPTH - 1)) {
			Graphics.field[i - 1][j + 1].flag();
		}
		if (i > 0 && j > 0) {
			Graphics.field[i - 1][j - 1].flag();
		}
	}
}
