package sprites;

import java.awt.event.KeyEvent;
import static graphicalUserInterface.Commons.BOARD_HEIGHT;
import static graphicalUserInterface.Commons.BOARD_WIDTH;


public class Hero extends Sprite  {

	private int dx;
	private int dy;
	private int life = 100;

	public Hero(int x, int y) {
		super(x, y);
		initializeHero();
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		
			this.life = life;
	

	}

	private void initializeHero() {
		loadImage("images.jpg");
		getImageDimensions();

	}

	public void moveHero() {
		x += dx;
		y += dy;

		if (x < 1) {
			x = 1;
		}

		if (y < 1) {
			y = 1;
		}
		if (x > (BOARD_WIDTH - imageWidth)) {
			x = BOARD_WIDTH - imageWidth;
		}
		if (y > (BOARD_HEIGHT - imageWidth)) {
			y = BOARD_HEIGHT - imageWidth;
		}
	}

	public int getX()

	{
		return x;

	}

	public int getY() {
		return y;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			dy = -2;
		}
		if (key == KeyEvent.VK_DOWN) {
			dy = 2;
		}

		if (key == KeyEvent.VK_LEFT) {
			dx = -2;

		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;

		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
			dx = 0;

		}
		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}
		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}

}
