package theGame;

import java.awt.Color;
import java.awt.Graphics;

public class Player2 extends GameObjects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3061778957083915138L;

	public Player2(int x, int y, ObjectID identity) {
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

		float notCrossXmargins = MainGame.posRelToMargins(getx(), MainGame.WIDTH / 2 - 20, MainGame.WIDTH - 116);
		float notCrossYmargins = MainGame.posRelToMargins(gety(), 0, MainGame.HEIGHT - 140);
		setX(notCrossXmargins);
		setY(notCrossYmargins);
	}

	@Override
	public void render(Graphics graph) {
		// TODO Auto-generated method stub

		graph.setColor(Color.GREEN);
		graph.fillOval((int) getx(), (int) gety(), 69, 69);

	}

}
