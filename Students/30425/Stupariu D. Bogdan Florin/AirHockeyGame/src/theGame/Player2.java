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
		
		setX((int) (getX() + getSpeedX()));
		setY((int) (getY() + getSpeedY()));
		
		setX( MainGame.posRelToMargins(getX(), MainGame.WIDTH/2-20, MainGame.WIDTH-116));
		setY( MainGame.posRelToMargins(getY(), 0, MainGame.HEIGHT-140));
		
	}

	@Override
	public void render(Graphics graph) {
		// TODO Auto-generated method stub

		graph.setColor(Color.GREEN);
		graph.fillOval(getX(), getY(), 69, 69);
		
	}
	


}
