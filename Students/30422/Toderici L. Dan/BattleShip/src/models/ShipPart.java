package models;

import java.awt.Point;

public class ShipPart 
{
	private Point location;
	
	private boolean isDestroyed;
	
	public ShipPart()
	{
		setLocation(new Point(-1, -1));
		setIsDestroyed(false);
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point loc) {
		this.location = loc;
	}

	public boolean getIsDestroyed() {
		return isDestroyed;
	}

	public void setIsDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
	
	
	
}
