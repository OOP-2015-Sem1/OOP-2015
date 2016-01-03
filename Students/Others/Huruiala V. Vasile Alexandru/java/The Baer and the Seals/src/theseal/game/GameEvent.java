package theseal.game;

import java.awt.Point;

public class GameEvent {
	private Point position;
	private String message;
	
	public GameEvent(Point position, String message) {
		super();
		this.position = position;
		this.message = message;
	}
	
	public Point getPosition() {
		return position;
	}
	public String getMessage() {
		return message;
	}
}
