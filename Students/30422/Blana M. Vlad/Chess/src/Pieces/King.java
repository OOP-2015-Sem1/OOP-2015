package Pieces;

public class King extends Piece {
	@Override
	public ListOfPieces getType() {
		return ListOfPieces.KING;
	}

	@Override
	public String possibleMove(int r, int c) {
		return "";
	}
}
