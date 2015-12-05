package cat.the.fluffeh.models;

import java.awt.Point;

public class Fluffeh {

	public static final int DEFAULT_VIEW_DISTANCE = 2;

	private int viewDistance;
	private Point position;

	public Fluffeh(Point position, int viewDistance) {
		this.position = position;
		this.viewDistance = viewDistance;
	}

	public Fluffeh(Point position) {
		this(position, DEFAULT_VIEW_DISTANCE);
	}

	public int getViewDistance() {
		return viewDistance;
	}

	public void setViewDistance(int viewDistance) {
		this.viewDistance = viewDistance;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
