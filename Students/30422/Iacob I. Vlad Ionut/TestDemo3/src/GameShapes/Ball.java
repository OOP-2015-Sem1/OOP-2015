package GameShapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Random;
import java.util.TreeSet;
import java.lang.Math;
import javax.swing.JFrame;

public class Ball extends Sound{

	private int xDirection;
	private int yDirection;
	public int x;
	public int area;
	public int y;
	public final int SCREEN_WIDTH, SCREEN_HEIGHT;
	private Color ballcolor;
	private int TIME_DELAY = 9;
	public TreeSet<Integer> xCoordinate = new TreeSet<Integer>();
	public TreeSet<Integer> yCoordinate = new TreeSet<Integer>();
	public static File brickHit = new File("brick.wav");
	public static File wallHit = new File("paddle.wav");
	
	public Ball(int x, int y, Color ballcolor, JFrame j)  {

		this.x = x;
		this.y = y;
		SCREEN_WIDTH = j.getWidth();
		SCREEN_HEIGHT = j.getHeight();
		do {
			xDirection = 2;
			yDirection = 2;
			if (Math.random() < .5) {
				xDirection *= -1;
			}
			if (Math.random() < .5) {
				yDirection *= -1;
			}
		} while (xDirection == 0 || yDirection == 0);

		this.ballcolor = ballcolor;
		area = 20;
	}

	public void xBrickCoordinates() {

		for (int i = 15; i <= 895; i += 110) {
			xCoordinate.add(i);
			// System.out.println(i);
		}
	}

	public void yBrickCoordinates() {
		for (int i = 35; i <= 235; i += 20) {
			yCoordinate.add(i);
			// System.out.println(i);
		}
	}

	public void move(Paddle paddle, Brick[][] samplebrick) {

		xBrickCoordinates();
		yBrickCoordinates();
		this.x += xDirection;
		this.y += yDirection;
		if (x < 0) {
			xDirection *= -1;
		}
		if (y < 0) {
			yDirection *= -1;
		}
		if (y + area  > SCREEN_HEIGHT) {
			
			yDirection *= -1;
			
		}
		if (x + area  > SCREEN_WIDTH) {
			xDirection *= -1;
			
		}
		if (y >= paddle.getY() && y < paddle.getY() + paddle.getHEIGHT()) {
			if ((x < paddle.getX() + paddle.getWIDTH() / 2 - 45)&&(x>50)) {
				yDirection *= -1;
				xDirection *= -1;
				PlaySound(wallHit);
				
			} else if ((x > paddle.getX() + paddle.getWIDTH() / 2 + 25)&&(x<350)) {
				yDirection *= -1;
				xDirection *= -1;
				PlaySound(wallHit);
				
			}
			else{
				yDirection*=-1;
				PlaySound(wallHit);
			}
		}

		if ((x >= 15 && x <= 895) & (y >= 35 && y <= 235)) {

			if (samplebrick[xCoordinate.floor(x)][yCoordinate.floor(y)].getVisibility() == false) {

				System.out.println("collision at" + xCoordinate.floor(x) + " " + y);

				if ((samplebrick[x][y].getY() <= yCoordinate.ceiling(y))
						|| (samplebrick[x][y].getY() >= yCoordinate.floor(y))) {

					yDirection *= -1;
					
				
				}
					System.out.println("collision at y");
				  
                samplebrick[xCoordinate.floor(x)][yCoordinate.floor(y)].setVisiblity(true);
                PlaySound(brickHit);
              
			}
		}
		try

		{
			Thread.sleep(TIME_DELAY);
		} catch (
				
		Exception e) {
			
		}
	}

	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}

	public void Paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(ballcolor);
		g2d.fillOval(x, y, area, area);
	}
}
