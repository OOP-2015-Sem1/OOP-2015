import java.awt.Color;
import java.awt.Graphics;

public class Player {
	private int x;
	private int y;
	private int y_speed = 0;
	private int width = 15;
	private int height = 80;

	public Player(int requiredX, int requiredY) {
		x = requiredX;
		y = requiredY;
	}

	public void update() {
		if (y < Pong.gui_HEIGHT - 120 && y > 0)
			y = y + y_speed;
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

	public void sety_speed(int speed) {
		y_speed = speed;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int get_width() {
		return width;
	}

	public int get_height() {
		return height;
	}
}