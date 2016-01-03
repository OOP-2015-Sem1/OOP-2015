package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.Random;

import theGame.MainGame;

public class Ball extends GameObject {

	public boolean collided = false;
	private boolean collideMargins;

	File Collision_sound = new File ("res/Laser_Shoot7.ogg");

	public Ball(int x, int y, ObjectID identity) {
		super(x, y, identity);
		// TODO Auto-generated constructor stub

		if (!collided) {
			generateRandomDirection();
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

		setX((int) (getx() + getSpeedX()));
		setY((int) (gety() + getSpeedY()));

		verifyMaxSpeed();

		collideMargins = gety() <= 0 || gety() >= MainGame.HEIGHT - 110 || getx() <= 0 || getx() >= MainGame.WIDTH - 90;

		if (collideMargins) {
			addFriction();
			verifyCollisionMargins();
		}
	}

	@Override
	public void render(Graphics graph) {
		// TODO Auto-generated method stub
		drawRedBall(graph);
	}

	private void drawRedBall(Graphics graph) {
		graph.setColor(Color.RED);
		graph.fillOval((int) getx(), (int) gety(), 49, 49);
	}

	private void generateRandomDirection() {
		// TODO Auto-generated method stub
		Random rand = new Random();

		int n1 = 0;
		int n2 = 0;

		while (n1 == 0 || n2 == 0) {
			n1 = rand.nextInt(7) - 4;
			n2 = rand.nextInt(7) - 4;
		}
		setSpeedX(n1);
		setSpeedY(n2);
	}

	private void verifyCollisionMargins() {
		// TODO Auto-generated method stub

		//audio = new Audio();
		
		if (gety() <= 0) {
			setY(1);
			setSpeedY(getSpeedY() * -1);
			//if (MainGame.soundMute == false) {
				//Audio.getSound("collision_sound").play();
			//}
		}

		if (gety() >= MainGame.HEIGHT - 120) {
			setY(MainGame.HEIGHT - 121);
			setSpeedY(getSpeedY() * -1);
			//if (MainGame.soundMute == false) {
				//Audio.getSound("collision_sound").play();
			//}
		}

		if (getx() <= 0) {
			setX(1);
			setSpeedX(getSpeedX() * -1);
			//if (MainGame.soundMute == false) {
				//Audio.getSound("collision_sound").play();
			//}
		}

		if (getx() >= MainGame.WIDTH - 90) {
			setX(MainGame.WIDTH - 89);
			setSpeedX(getSpeedX() * -1);
			//if (MainGame.soundMute == false) {
				
			//}
		}
	}

	private void addFriction() {
		// TODO Auto-generated method stub
		setSpeedY((float) (getSpeedY() / 1.5));
		setSpeedX((float) (getSpeedX() / 1.5));
	}

	public void verifyMaxSpeed() {
		if (getSpeedX() > 10)
			setSpeedX(10);
		if (getSpeedY() > 10)
			setSpeedY(10);
		if (getSpeedX() < -10)
			setSpeedX(-10);
		if (getSpeedY() < -10)
			setSpeedY(-10);
	}

}