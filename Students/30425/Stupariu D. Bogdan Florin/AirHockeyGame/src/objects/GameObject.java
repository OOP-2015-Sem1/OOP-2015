package objects;

import java.awt.Graphics;

public abstract class GameObject  {
	private float x;
	private float y;
	protected ObjectID identity;
	private float speedX, speedY;
	private float angle;

	public GameObject(float x, float y, ObjectID identity) {
		this.x = x;
		this.y = y;
		this.identity = identity;

	}

	public abstract void tick();

	public abstract void render(Graphics graph);

	public void setX(float x) {
		this.x = x;
	}

	public float getx() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float gety() {
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

	public float gCenterPX(float x, float width) {
		return width / 2 - x;
	}

	public float gCenterPY(float y, float height) {
		return height / 2 - y;
	}

}
