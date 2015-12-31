import java.awt.Color;
import java.awt.Graphics;

public class Player2 {
	private int x = 400;
	private int y;

	private int y_speed = 0;
	private int width = 15;
	private int height = 40;

	public Player2() {

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
}
