package entities;

import game.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball {
	private int y = Game.gui_HEIGHT / 2;
	private int x = Game.gui_WIDTH / 2;
	private int x_speed = -5;
	private int y_speed = 5;
	private int size = 20;
	private int speed ;
	private boolean update1=false;
	private boolean update2=false;
	Color ballColor= Color.RED;

	public Ball(int speed){
		this.speed=speed;
	}
	
	public void update() {
		x = x + x_speed;
		y = y + y_speed;

		if (x < 0) {
			x_speed = speed;
			ballColor=Color.BLACK;
			y = Game.gui_HEIGHT / 2;
			x = Game.gui_WIDTH / 2;
			update2=true;

		} else if (x + size > Game.gui_WIDTH ) {
			x_speed = -speed;
			ballColor=Color.BLACK;
			y = Game.gui_HEIGHT / 2;
			x = Game.gui_WIDTH / 2;
			update1=true;

		}
		if (y < 0) {
			y_speed = speed;
		} else if (y + size > Game.gui_HEIGHT - 33) {
			y_speed = -speed;
		}
	}
	
	public void setSpeed(int value){
		this.speed=value;
	}
	
	public void setUpdate1(boolean value){
		update1=value;
	}
	
	public void setUpdate2(boolean value){
		update2=value;
	}
	
	public boolean getUpdate1(){
		return update1;
	}
	
	public boolean getUpdate2(){
		return update2;
	}

	public void paint(Graphics g) {
		g.setColor(ballColor);
		g.fillOval(x, y, size, size);
	}

	private void changeDirection() {
		x_speed = -x_speed;

	}
	
	public void setBallColor(Color color)
	{
		this.ballColor=color;
	}
	
	public void checkCollisionwith(Player player1) {
		Rectangle ball = new Rectangle(x, y, size, size);
		Rectangle player = new Rectangle(player1.getX(), player1.getY(), player1.get_width(), player1.get_height());
		if (ball.intersects(player)) {
			changeDirection();
		}

	}
	
	public void checkCollisionwith(Block player1) {
		Rectangle ball = new Rectangle(x, y, size, size);
		Rectangle player = new Rectangle(player1.getX(), player1.getY(), player1.get_width(), player1.get_height());
		if (ball.intersects(player)) {
			changeDirection();
		}

	}
}
