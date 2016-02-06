package river.models;

import java.awt.Point;

public class Pole extends Entity{
	
	public Pole(Point position){
		setPosition(position);
	}
	
	
	@Override
	public char getBoardRepresentation() {
		return 'P';
	}
	
}
