package Pieces;

public class Knight extends Piece {
	@Override
	public ListOfPieces getType() {
		return ListOfPieces.KNIGHT;
	}

	@Override
	public String possibleMove(int r, int c) {
		return "";
	}
}
