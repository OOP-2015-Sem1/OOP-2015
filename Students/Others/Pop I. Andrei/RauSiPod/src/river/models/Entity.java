package river.models;

import java.awt.Point;

public abstract class Entity {

	private Point position;
		
	public Entity() {
		this(new Point(0,0));
	}
	
	public Entity(Point position) {
		this.position = position;
	}
	
	public Point getPosition() {
		return position;
	}
	
	protected void setPosition(Point position) {
		this.position = position;
	}
	
	public abstract char getBoardRepresentation();
	
}
