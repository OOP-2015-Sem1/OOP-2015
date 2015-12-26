package theGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball extends GameObjects {

	private static final long serialVersionUID = -7194903560154850305L;

	boolean collided = false;

	public Ball(int x, int y, ObjectID identity) {
		super(x, y, identity);
		// TODO Auto-generated constructor stub

		if (!collided) {
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

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

		setX((int) (getX() + getSpeedX()));
		setY((int) (getY() + getSpeedY()));

		// -- friction ----------------------

		// -- collision with margins-------
		if (getY() <= 0 || getY() >= MainGame.HEIGHT - 110 || getX() <= 0 || getX() >= MainGame.WIDTH - 90) {
			//----- friction
			setSpeedY((float) (getSpeedY()/1.75));
			setSpeedX((float) (getSpeedX()/1.75));
			
			if (getY() <= 0) {
				setY(1);
				setSpeedY(getSpeedY() * -1);

			}

			if (getY() >= MainGame.HEIGHT - 110) {
				setY(MainGame.HEIGHT - 109);
				setSpeedY(getSpeedY() * -1);
			}

			if (getX() <= 0) {
				setX(1);
				setSpeedX(getSpeedX() * -1);
			}

			if (getX() >= MainGame.WIDTH - 90) {
				setX(MainGame.WIDTH - 89);
				setSpeedX(getSpeedX() * -1);
			}
		}
	}

	@Override
	public void render(Graphics graph) {
		// TODO Auto-generated method stub

		graph.setColor(Color.RED);
		graph.fillOval(getX(), getY(), 49, 49);

	}

	

}