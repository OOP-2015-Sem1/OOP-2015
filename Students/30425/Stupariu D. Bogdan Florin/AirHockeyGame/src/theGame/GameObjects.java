package theGame;

import java.awt.Graphics;

import javax.swing.JFrame;

public abstract class GameObjects extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4159401302361680283L;

	private int x;
	private int y;
	private ObjectID identity;
	private float speedX, speedY;
	private float angle;

	public GameObjects(int x, int y, ObjectID identity) {
		this.x = x;
		this.y = y;
		this.identity = identity;

	}

	public abstract void tick();

	public abstract void render(Graphics graph);

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setObjectID(ObjectID identity) {
		this.identity = identity;
	}

	public ObjectID getObjectID() {
		return identity;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getAngle() {
		return angle;
	}

	public int gCenterPX(int x, int width) {
		return width / 2 - x;
	}

	public int gCenterPY(int y, int height) {
		return height / 2 - y;
	}

}
