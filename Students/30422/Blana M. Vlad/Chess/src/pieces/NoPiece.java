package pieces;

import java.util.ArrayList;
import java.util.List;

import Main.Controller;
import Main.Movement;

public class NoPiece extends Piece {
	@Override
	public Pieces getType() {
		return Pieces.NOPIECE;
	}

	@Override
	public List<Movement> possibleMove(int row, int column,
			Piece[][] chessBoard, boolean checkKingSafety, Controller controller) {
		List<Movement> list = new ArrayList<Movement>();
		return list;
	}
}
