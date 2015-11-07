package bear.and.seals.models;

import java.awt.Point;

public class Hole extends Entity {
	
	public Hole(Point point) {
		super(point);
	}

	@Override
	public char getBoardRepresentation() {
		return 'H';
	}

}
