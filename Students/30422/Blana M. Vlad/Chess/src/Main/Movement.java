package Main;

import java.awt.Point;

public class Movement {
	public Point source = new Point();
	public Point destination = new Point();
	public boolean capturedPiece;

	public Movement() {

	}

	public boolean equals(Movement move) {
		if (this.source.x == move.source.x
				&& this.destination.x == move.destination.x
				&& this.source.y == move.source.y
				&& this.destination.y == move.destination.y)
			return true;
		else
			return false;
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
		return "" + source.x + source.y + destination.x + destination.y;
	}
}
