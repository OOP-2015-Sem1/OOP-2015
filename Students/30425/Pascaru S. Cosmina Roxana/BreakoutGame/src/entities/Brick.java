package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import breakoutGame.Game;

public class Brick extends Entity{
	
	//brick details
	 int brickX = 100, brickY = 50, brickSpace = 70;
	 int brickWidth =60, brickHeight=16;
	Color brickColor;
	private boolean hit;
	
	//constructor
	public Brick(Game game,  Color color, int x, int y){
		this.game = game;
		brickX=x;
		brickY=y;
		brickColor = color;
		entityCollider = new Rectangle(brickX, brickY, brickWidth, brickHeight);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(brickColor);
		g.fillRect(brickX, brickY, brickWidth, brickHeight);
	}

	@Override
	public void update() {
		
	}
	
	public void wasHit(boolean var){
		hit = true;
	}
	
	public boolean isHit(){
		return hit;
	}

}
