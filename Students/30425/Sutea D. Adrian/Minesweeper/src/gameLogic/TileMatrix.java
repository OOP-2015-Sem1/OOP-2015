package gameLogic;

import javax.swing.*;
import java.util.Random;

public class TileMatrix {
	private Tile tile;
	private int[] types = { 0, 1, 2, 3, 4, 5, 6, 7, 8, -1 };// 0 is empty tile,
															// -1 is mine tile
															// and the others
															// are the numbers
															// of mines near it
	private int width = 16;
	private int height = 16;
	private boolean ok;
	private Random rnd1, rnd2;
	private int type;
	private int nrOfMines = 40;
	private Tile[][] tileMatrix = new Tile[width][height];

	public TileMatrix() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tile = new Tile();
				tile.setType(types[0]);
				tile.setIcon(new ImageIcon("resources//Minesweeper_0.png"));
				tileMatrix[i][j] = tile;
			}
		}
		for (int i = 0; i < nrOfMines; i++) {
			ok = true;
			rnd1 = new Random();
			rnd2 = new Random();
			while (ok == true) {
				int minePointX = rnd1.nextInt(width);
				int minePointY = rnd2.nextInt(height);
				type = tileMatrix[minePointX][minePointY].getType();
				if (type != types[9]) {
					tileMatrix[minePointX][minePointY].setType(types[9]);// types[9]=mine
					tileMatrix[minePointX][minePointY].setIcon(new ImageIcon("resources//mine.png"));
					increaseTypeOfNeighbours(minePointX, minePointY);
					ok = false;
				}
			}
		}
	}

	public void increaseTypeOfNeighbours(int x, int y) {
		if (x - 1 >= 0 && y - 1 >= 0 && tileMatrix[x - 1][y - 1].getType() != types[9]) {
			increaseTileType(x - 1, y - 1);

		}
		if (y - 1 >= 0 && tileMatrix[x][y - 1].getType() != types[9]) {
			increaseTileType(x, y - 1);
		}
		if (y - 1 >= 0 && x + 1 < width && tileMatrix[x + 1][y - 1].getType() != types[9]) {
			increaseTileType(x + 1, y - 1);
		}
		if (x - 1 >= 0 && tileMatrix[x - 1][y].getType() != types[9]) {
			increaseTileType(x - 1, y);
		}
		if (x + 1 < width && tileMatrix[x + 1][y].getType() != types[9]) {
			increaseTileType(x + 1, y);
		}
		if (x - 1 >= 0 && y + 1 < height && tileMatrix[x - 1][y + 1].getType() != types[9]) {
			increaseTileType(x - 1, y + 1);
		}
		if (y + 1 < height && tileMatrix[x][y + 1].getType() != types[9]) {
			increaseTileType(x, y + 1);
		}
		if (y + 1 < height && x + 1 < width && tileMatrix[x + 1][y + 1].getType() != types[9]) {
			increaseTileType(x + 1, y + 1);
		}
	}

	public void increaseTileType(int x, int y) {
		type = tileMatrix[x][y].getType();
		type++;
		tileMatrix[x][y].setType(types[type]);
		tileMatrix[x][y].setIcon(new ImageIcon("resources//Minesweeper_" + types[type] + ".png"));
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getNrOfMines() {
		return nrOfMines;
	}

	public int getTypeFromMatrix(int x, int y) {
		return tileMatrix[x][y].getType();
	}

	public ImageIcon getIconFromMatrix(int x, int y) {
		return tileMatrix[x][y].getIcon();
	}
}