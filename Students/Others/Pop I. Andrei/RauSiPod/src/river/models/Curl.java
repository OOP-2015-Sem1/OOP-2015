package river.models;

import java.awt.Point;

public class Curl extends Entity{
	
	private boolean isOnRiver;
	
	public Curl(Point position) {
		super(position);
		isOnRiver=false;
	}
	
	public void  move(Point position) {
		setPosition(position);
	}
	
	public boolean isOnRiver() {
		return isOnRiver;
	}
	
	public void setOnRiver(boolean state){
		this.isOnRiver = state;
	}
	
	@Override
	public char getBoardRepresentation() {
		return 'C';
	}

}
