import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	private int x = Pong.gui_HEIGHT / 2;
	private int y = Pong.gui_WIDTH / 2;
	private int x_speed = -4;
	private int y_speed = 4;
	private int size = 10;

	public void update() {
		x = x + x_speed;
		y = y + y_speed;

		if (x < 0) {
			x_speed = 4;
		} else if (x + size > Pong.gui_WIDTH - 7) {
			x_speed = -4;
		}
		if (y < 0) {
			y_speed = 4;
		} else if (y + size > Pong.gui_HEIGHT - 33) {
			y_speed = -4;
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, size, size);
	}
	private void changeDirection() {
		x_speed = -x_speed;
	}
	
	public void checkCollisionwith(Player1 player1) {
		if (this.x > player1.getX() && this.x < player1.getY() + player1.get_width()) {
			if (this.y > player1.getY() && this.y < player1.getY() + player1.get_height()) {
				changeDirection();
			}
		}
	}

}
