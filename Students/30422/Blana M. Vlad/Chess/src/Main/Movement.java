package Main;

import java.awt.Point;

public class Movement {
	public Point source = new Point();
	public Point destination = new Point();

	public Movement() {

	}

	public void setMove(int xSource, int ySource, int xDestination,
			int yDestination) {
		this.source.x = xSource;
		this.source.y = ySource;
		this.destination.x = xDestination;
		this.destination.y = yDestination;
	}

	public String encodeMoveToString() {
		// String string
		return "" + source.x + source.y + destination.x + destination.y;
	}
}
