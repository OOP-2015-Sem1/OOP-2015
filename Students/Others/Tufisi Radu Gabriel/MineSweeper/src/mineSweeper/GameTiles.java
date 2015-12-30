package mineSweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameTiles {
	private BufferedImage normal;
	private BufferedImage openedImage;
	private BufferedImage mineImage;
	private BufferedImage flag;
	private int x;
	private int y;
	private boolean mine;
	private boolean opened;
	private int amountOfNearBombs;

	private static int width = Frame.getGameWidth() / World.getWidth();
	private static int heigth = Frame.getGameWidth() / World.getHeigth();

	public GameTiles(int x, int y, BufferedImage normal, BufferedImage mine, BufferedImage openedImage,
			BufferedImage flag) {
		this.x = x;
		this.y = y;
		this.normal = normal;
		this.mineImage = mine;
		this.openedImage = openedImage;
		this.flag = flag;

		
	}

	public void draw(Graphics g) {
		if (!opened)
			g.drawImage(normal, x * width, y * heigth, null);
		else {
			if (mine)
				g.drawImage(mineImage, x * width, y * heigth, null);
			else {
				g.drawImage(openedImage, x * width, y * heigth, null);
				if (amountOfNearBombs > 0) {
					g.setColor(Color.BLUE);
					g.drawString("" + amountOfNearBombs, x * width+7, y * heigth + heigth-4);//Pt a aranja ok numerele
				}
			}
		}

	}

	public void setNormal(BufferedImage normal) {
		this.normal = normal;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeigth() {
		return heigth;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpenedImage(BufferedImage openedImage) {
		this.openedImage = openedImage;
	}

	public boolean isMine() {
		return mine;
	}

	public void setAmountOfNearBombs(int amountOfNearBombs) {
		this.amountOfNearBombs = amountOfNearBombs;
	}

}
