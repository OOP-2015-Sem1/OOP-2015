package pieces;

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

	public abstract String possibleMove(int row, int column,
			Piece[][] chessBoard);
}
