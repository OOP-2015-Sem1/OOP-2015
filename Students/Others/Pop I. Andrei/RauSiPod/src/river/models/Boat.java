package river.models;

import java.awt.Point;

public class Boat extends Entity{
	
	public Boat(Point position) {
		super(position);
	}
	
	public void move(Point position){
		setPosition(position);
	}
	
	@Override
	public char getBoardRepresentation() {
		return 'B';
	}

}
