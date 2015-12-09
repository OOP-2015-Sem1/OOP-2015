package theseal.model;

import java.awt.Image;
import java.awt.Point;

public abstract class Entity implements Drawable {
	private Point position;
	private Image image;

	public Entity(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
