package battleship.models;

import java.awt.Point;

public abstract class ShipModel {
	
	private String name;
	protected int size;
	private int life;
	protected String orientation = "";
	protected Point location;
	private boolean isDestroyed = false;
	private boolean placedOnBoard = false;
	
	
	public Point getLocation() {
		return location;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public boolean isDestroyed() {
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getOrientation() {
		return this.orientation;
	}
	public void setPlacedOnBoard(boolean placed) {
		this.placedOnBoard = placed;
	}
	public boolean isPlacedOnBoard() {
		return placedOnBoard;
	}	
}
