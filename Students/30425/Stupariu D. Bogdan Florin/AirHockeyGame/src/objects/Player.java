package objects;

import java.awt.Color;
import java.awt.Graphics;

import theGame.MainGame;

public class Player extends GameObject {

	public Player(int x, int y, ObjectID identity) {
		super(x, y, identity);
		// TODO Auto-generated constructor stub
		setSpeedX(0);
		setSpeedY(0);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

		setX(getx() + getSpeedX());
		setY(gety() + getSpeedY());
		if (identity == ObjectID.Player) {
			float notCrossXmargins = MainGame.posRelToMargins(getx(), 0, MainGame.WIDTH / 2 - 90);
			float notCrossYmargins = MainGame.posRelToMargins(gety(), 0, MainGame.HEIGHT - 140);
			setX(notCrossXmargins);
			setY(notCrossYmargins);
		} else if (identity == ObjectID.Player2) {
			float notCrossXmargins = MainGame.posRelToMargins(getx(), MainGame.WIDTH / 2 - 20, MainGame.WIDTH - 116);
			float notCrossYmargins = MainGame.posRelToMargins(gety(), 0, MainGame.HEIGHT - 140);
			setX(notCrossXmargins);
			setY(notCrossYmargins);
		}

	}

	@Override
	public void render(Graphics graph) {
		// TODO Auto-generated method stub

		if (identity == ObjectID.Player) {
			graph.setColor(Color.BLUE);
			graph.fillOval((int) getx(), (int) gety(), 69, 69);
		} else if (identity == ObjectID.Player2) {
			graph.setColor(Color.GREEN);
			graph.fillOval((int) getx(), (int) gety(), 69, 69);
		}
	}

}
