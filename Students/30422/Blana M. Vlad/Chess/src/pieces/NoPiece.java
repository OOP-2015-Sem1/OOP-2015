package pieces;

public class NoPiece extends Piece {
	@Override
	public Pieces getType() {
		return Pieces.NOPIECE;
	}

	@Override
	public String possibleMove(int row, int column, Piece[][] chessBoard) {
		return "";
	}
}
