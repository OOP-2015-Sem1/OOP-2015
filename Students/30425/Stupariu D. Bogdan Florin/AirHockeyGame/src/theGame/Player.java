package theGame;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObjects {

	private static final long serialVersionUID = 8129985812140590066L;

	public Player(int x, int y, ObjectID identity) {
		super(x, y, identity);
		// TODO Auto-generated constructor stub
		setSpeedX(0);
		setSpeedY(0);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

		setX((int) (getX() + getSpeedX()));
		setY((int) (getY() + getSpeedY()));

		setX(MainGame.posRelToMargins(getX(), 0, MainGame.WIDTH / 2 - 90));
		setY(MainGame.posRelToMargins(getY(), 0, MainGame.HEIGHT - 140));

	}

	@Override
	public void render(Graphics graph) {
		// TODO Auto-generated method stub

		graph.setColor(Color.BLUE);
		graph.fillOval(getX(), getY(), 69, 69);

	}

	
}
