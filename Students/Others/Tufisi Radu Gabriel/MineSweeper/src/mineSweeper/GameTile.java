package mineSweeper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameTile {
	private BufferedImage normal;
	private BufferedImage openedImage;
	private BufferedImage mineImage;
	private BufferedImage flagImage;
	private int x;
	private int y;
	private boolean mine;
	private boolean opened;
	private boolean flag;
	private int amountOfNearBombs;

	private static int width = Frame.getGameWidth() / World.getWidth();
	private static int heigth = Frame.getGameWidth() / World.getHeigth();

	public GameTile(int x, int y, BufferedImage normal, BufferedImage mine, BufferedImage openedImage,
			BufferedImage flag) {
		this.x = x;
		this.y = y;
		this.normal = normal;
		this.mineImage = mine;
		this.openedImage = openedImage;
		this.flagImage = flag;

		
	}

	public void draw(Graphics g) {
		if (!opened){
			
			if(!flag) g.drawImage(normal, x * width, y * heigth, null);
			else{
				g.drawImage(flagImage, x * width, y * heigth, null);
			}
			}
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
	public boolean isFlag() {
		return flag;
	}

	public void setOpenedImage(BufferedImage openedImage) {
		this.openedImage = openedImage;
	}

	public boolean isMine() {
		return mine;
	}
	public int getAmountOfNearBombs(){
		return amountOfNearBombs;
	}

	public void setAmountOfNearBombs(int amountOfNearBombs) {
		this.amountOfNearBombs = amountOfNearBombs;
	}
	public boolean canOpen(){
		return !opened&&!mine&&amountOfNearBombs>=0;
	}
	public void placeFlag(){
		if(flag) flag =false;
		else{
			if(!opened){
				flag=true;
			}
		}
	}

}
