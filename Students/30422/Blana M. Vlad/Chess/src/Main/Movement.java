package Main;

import java.awt.Point;

public class Movement {
	public Point source = new Point();
	public Point destination = new Point();
	public boolean capturedPiece;

	public Movement() {

	}

	public void setMove(int xSource, int ySource, int xDestination,
			int yDestination, boolean capturedPiece) {
		this.source.x = xSource;
		this.source.y = ySource;
		this.destination.x = xDestination;
		this.destination.y = yDestination;
		this.capturedPiece = capturedPiece;
	}

	public String encodeMoveToString() {
		// String string
		return "" + source.x + source.y + destination.x + destination.y;
	}
}
