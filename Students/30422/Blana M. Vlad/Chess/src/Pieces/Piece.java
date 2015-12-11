package Pieces;

public class Piece {
	private Colors color;

	public void setColor(Colors color) {
		this.color = color;
	}

	public ListOfPieces getType() {
		return ListOfPieces.NOPIECE;
	}

	public Colors getColor() {
		return this.color;
	}

	public String possibleMove(int r, int c) {
		return "pula";
	}
}
