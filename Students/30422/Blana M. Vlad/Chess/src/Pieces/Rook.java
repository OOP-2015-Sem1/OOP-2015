package Pieces;

public class Rook extends Piece {
	@Override
	public ListOfPieces getType() {
		return ListOfPieces.ROOK;
	}

	@Override
	public String possibleMove(int r, int c) {
		return "";
	}
}
