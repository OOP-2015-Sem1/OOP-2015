package entities;
import java.awt.Color;
import java.awt.Graphics;

import game.Game;

public class Player {
	private int x;
	private int y;
	private int y_speed = 0;
	private final static int WIDTH = 15;
	private final static int HEIGHT = 120;
	private String name= "Player";
	private int score = 0;

	public Player(int requiredX, int requiredY) {
		x=requiredX;
		y=requiredY;
	}

	public void update() {
		if (y >= Game.gui_HEIGHT - HEIGHT-30) {
			y = Game.gui_HEIGHT - HEIGHT - 31;

		}
		if (y < 1) {
			y = 1;
		}
		if (y < Game.gui_HEIGHT - HEIGHT-30 && y > 0)
			y = y + y_speed;
	}
	
	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, HEIGHT);
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
		return WIDTH;
	}

	public int get_height() {
		return HEIGHT;
	}
	public void setScore(int score){
		this.score = score;
		
	}
	public int getScore(){
		return score;
	}
}