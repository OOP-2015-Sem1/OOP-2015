package mineSweeper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;

import org.omg.Messaging.SyncScopeHelper;

public class World {
	private static int width = 20;
	private static int heigth = 20;
	private final int nr_of_bombs = 20; 
	private Random random;
	private GameTiles[][] tiles;

	private BufferedImage mine = LoadImg.scale(LoadImg.loadImage("icons/mine.gif"), GameTiles.getWidth(),
			GameTiles.getHeigth());
	private BufferedImage flag = LoadImg.scale(LoadImg.loadImage("icons/flag.gif"), GameTiles.getWidth(),
			GameTiles.getHeigth());
	private BufferedImage square = LoadImg.scale(LoadImg.loadImage("icons/unclicked.gif"), GameTiles.getWidth(),
			GameTiles.getHeigth());
	private BufferedImage pressedsquare = LoadImg.scale(LoadImg.loadImage("icons/blank.gif"), GameTiles.getWidth(),
			GameTiles.getHeigth());
	private BufferedImage normal;

	public World() {
		random = new Random();

		tiles = new GameTiles[width][heigth];
		for (int x = 0; x < width; x++) {

			for (int y = 0; y < heigth; y++) {

				tiles[x][y] = new GameTiles(x, y, normal, mine,pressedsquare, flag);
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
				int mx=x-1;
				int gx=x+1;
				int my=y-1;
				int gy=y+1;
				
				int amountOfBombs=0;
				if(mx>=0&&my>=0&&tiles[mx][my].isMine())amountOfBombs++;
				if(mx>=0&&tiles[mx][y].isMine())amountOfBombs++;
				if(mx>=0&&gy<heigth&&tiles[mx][gy].isMine())amountOfBombs++;
				
				if(my>=0&&tiles[x][my].isMine())amountOfBombs++;
				if(gy<heigth&&tiles[x][gy].isMine())amountOfBombs++;
				
				if(gx<width&&my>=0&&tiles[gx][my].isMine())amountOfBombs++;
				if(gx<width&&tiles[gx][y].isMine())amountOfBombs++;
				if(gx<width&&gy<heigth&&tiles[gx][gy].isMine())amountOfBombs++;
				
				tiles[x][y].setAmountOfNearBombs(amountOfBombs);
			}			
		}
	}
	public void clicked(int x,int y){
		int tileX=x/width;
		int tileY=y/heigth; 
		
		tiles[tileX][tileY].setOpened(true);
	}

	public void draw(Graphics g) {

		for (int x = 0; x < width; x++) {

			for (int y = 0; y < heigth; y++) {
				tiles[x][y].draw(g);
			}
		}
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeigth() {
		return heigth;
	}

}
