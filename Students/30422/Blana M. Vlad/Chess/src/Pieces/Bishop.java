package Pieces;

public class Bishop extends Piece {
	@Override
	public ListOfPieces getType() {
		return ListOfPieces.BISHOP;
	}

	@Override
	public String possibleMove(int r, int c) {
		return "bishop";
	}
}
