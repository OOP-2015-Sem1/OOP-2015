package Pieces;

public class Queen extends Piece {
	@Override
	public ListOfPieces getType() {
		return ListOfPieces.QUEEN;
	}

	@Override
	public String possibleMove(int r, int c) {
		return "queen";
	}
}
