import java.awt.Color;
import java.awt.Graphics;

public class Player1 {
	private int y = Pong.gui_HEIGHT / 2;
	private int y_speed = 0;
	private int width = 15;
	private int height = 40;

	public Player1() {

	}

	public void update() {
		y = y + y_speed;
	}

	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(75, y, width, height);
	}

	public void sety_speed(int speed) {
		y_speed = speed;
	}
	public int getX() {
		return 50;
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
