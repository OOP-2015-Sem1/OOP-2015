package mineSweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;

import org.omg.Messaging.SyncScopeHelper;

public class World {
	private static int width = 20;
	private static int heigth = 20;
	private final int nr_of_bombs = 60;
	private Random random;
	private GameTile[][] tiles;
	private boolean finish;
	private boolean dead;

	private BufferedImage mine = LoadImg.scale(LoadImg.loadImage("/mine.gif"), GameTile.getWidth(),
			GameTile.getHeigth());
	private BufferedImage flag = LoadImg.scale(LoadImg.loadImage("/flag.gif"), GameTile.getWidth(),
			GameTile.getHeigth());
	private BufferedImage square = LoadImg.scale(LoadImg.loadImage("/unclicked.gif"), GameTile.getWidth(),
			GameTile.getHeigth());
	private BufferedImage pressedsquare = LoadImg.scale(LoadImg.loadImage("/blank.gif"), GameTile.getWidth(),
			GameTile.getHeigth());
	private BufferedImage normal;

	public World() {
		random = new Random();

		tiles = new GameTile[width][heigth];
		for (int x = 0; x < width; x++) {

			for (int y = 0; y < heigth; y++) {

				tiles[x][y] = new GameTile(x, y, normal, mine, pressedsquare, flag);
				tiles[x][y].setNormal(square);
			}
		}
		setMines();
		setNumbers();
	}

	private void setMines() {
		for (int i = 0; i < nr_of_bombs; i++) {
			setOneMine();
		}
	}

	private void setOneMine() {
		int x = random.nextInt(width);
		int y = random.nextInt(heigth);
		if (!tiles[x][y].isMine()) {
			tiles[x][y].setMine(true);
		} else
			setOneMine();
	}

	private void setNumbers() {
		for (int x = 0; x < width; x++) {

			for (int y = 0; y < heigth; y++) {
				int mx = x - 1;
				int gx = x + 1;
				int my = y - 1;
				int gy = y + 1;

				int amountOfBombs = 0;
				if (mx >= 0 && my >= 0 && tiles[mx][my].isMine())
					amountOfBombs++;
				if (mx >= 0 && tiles[mx][y].isMine())
					amountOfBombs++;
				if (mx >= 0 && gy < heigth && tiles[mx][gy].isMine())
					amountOfBombs++;

				if (my >= 0 && tiles[x][my].isMine())
					amountOfBombs++;
				if (gy < heigth && tiles[x][gy].isMine())
					amountOfBombs++;

				if (gx < width && my >= 0 && tiles[gx][my].isMine())
					amountOfBombs++;
				if (gx < width && tiles[gx][y].isMine())
					amountOfBombs++;
				if (gx < width && gy < heigth && tiles[gx][gy].isMine())
					amountOfBombs++;

				tiles[x][y].setAmountOfNearBombs(amountOfBombs);
			}
		}
	}

	public void clickedLeft(int x, int y) {
		//System.out.println(x + ";" + y);
		if(!dead&&!finish){
		int tileX = x / width;
		int tileY = y / heigth;
		if (!tiles[tileX][tileY].isFlag()) {

			tiles[tileX][tileY].setOpened(true);

			if (tiles[tileX][tileY].isMine()) {
				dead = true;
			} else {
				if (tiles[tileX][tileY].getAmountOfNearBombs() == 0) {
					openMore(tileX, tileY);

				}
			}
			checkFinish();
		}
	}
	}

	public void clickedRight(int x, int y) {
		if(!dead&&!finish){
		int tileX = x / width;
		int tileY = y / heigth;
		tiles[tileX][tileY].placeFlag();
		checkFinish();
	}
	}
	private void checkFinish() {
		finish = true;
		outer: for (int x = 0; x < width; x++) {

			for (int y = 0; y < heigth; y++) {
				if (!(tiles[x][y].isOpened() || tiles[x][y].isMine() && tiles[x][y].isFlag())) {
					finish = false;
					break outer;
				}
			}
		}
	}

	private void openMore(int x, int y) {
		tiles[x][y].setOpened(true);
		if (tiles[x][y].getAmountOfNearBombs() == 0) {
			int mx = x - 1;
			int gx = x + 1;
			int my = y - 1;
			int gy = y + 1;
			if (mx >= 0 && tiles[mx][y].canOpen())
				openMore(mx, y);
			if (gx < width && tiles[gx][y].canOpen())
				openMore(gx, y);
			if (my >= 0 && tiles[x][my].canOpen())
				openMore(x, my);
			if (gy < heigth && tiles[x][gy].canOpen())
				openMore(x, gy);
		}
	}

	public void draw(Graphics g) {

		for (int x = 0; x < width; x++) {

			for (int y = 0; y < heigth; y++) {
				tiles[x][y].draw(g);
			}
		}
		if (dead) {
			g.setColor(Color.RED);
			g.drawString("You lost!", 10, 30);
		} else if (finish) {
			g.setColor(Color.GREEN);
			g.drawString("You Won!", 10, 30);
		}
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeigth() {
		return heigth;
	}

}
