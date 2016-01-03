package pieces;

import java.util.List;

import Main.Controller;
import Main.Movement;

public abstract class Piece {
	private Colors color;

	/*
	 * public Piece(Colors color) { this.setColor(color); }
	 */
	public void setColor(Colors color) {
		this.color = color;
	}

	public abstract Pieces getType();

	public Colors getColor() {
		return this.color;
	}

	public abstract List<Movement> possibleMove(int row, int column,
			Piece[][] chessBoard, boolean checkKingSafety, Controller controller);
}
