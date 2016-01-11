package bricks.game;

import java.awt.Color;

import java.awt.Graphics2D;

public class Brick {

	public static Brick brick;


	int x, y, width = 60, height = 23;
	
	
	public boolean DeadOrAlive = false;

	
	public Brick(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(Graphics2D g, Color color, int x, int y) {
		
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		
		
	}
	
	public boolean getVisibility() {
		 return DeadOrAlive;
	}
		 
	public void setVisiblity(boolean TypeOFVisibility) {
		 	DeadOrAlive = TypeOFVisibility;
	}
	


}
