package Data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Clock.Delta;

import org.newdawn.slick.opengl.Texture;

public class PlayerIcon {
	private int x, y, width, height;
	private float speed;
	Texture texture;
	private boolean first = true;;
	
	public PlayerIcon(Texture texture, int x, int y, int width, int height, float speed) {
		this.texture = texture;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setSpeed(speed);
	}
	
	public void Draw() {
		DrawQuadTex(texture, x, y, width, height);
	}
	
	public void update() {
		if(first)
			first = false;
		else
			x += Delta() * speed;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}


}
