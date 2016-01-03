package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends Entity {

	// ball details
	public int ballX = 200;
	public int ballY = 200;
	public int ballDiameter = 16;
	public int ballSpeed = 5;
	public int ballXmove = ballSpeed;
	public int ballYmove = ballSpeed;

	// constructor
	public Ball() {
		surface = new Rectangle(ballX, ballY, ballDiameter, ballDiameter);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, ballDiameter, ballDiameter);
	}

	@Override
	public void update() {
		ballX += ballXmove;
		ballY += ballYmove;
		surface.setLocation(ballX, ballY);
	}

	public void setBallSpeed(int speed) {
		ballSpeed = speed;
	}
	public void restartPosition(){
		ballX = 200;
		ballY = 200;
		
		ballXmove = ballSpeed;
		ballYmove = ballSpeed;
		
		ballX += ballXmove;
		ballY += ballYmove;
		
		
	}

}
