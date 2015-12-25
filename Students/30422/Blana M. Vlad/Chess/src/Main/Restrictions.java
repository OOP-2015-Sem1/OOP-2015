package Main;

import pieces.Colors;
import pieces.Piece;

public class Restrictions {

	public static boolean kingSafety(Piece[][] chessBoard, Colors color,
			Controller controller) {
		String list = "";
		String whiteKingPosition = controller.whiteKingX
				+ controller.whiteKingY + "p";
		String blackKingPosition = controller.blackKingX
				+ controller.blackKingY + "p";
		controller.board.flipBoard(chessBoard);
		// controller.whiteTurn = !controller.whiteTurn;
		if (color == Colors.WHITE) {
			list += controller.movementManager.generateAllMoves(chessBoard,
					Colors.BLACK, controller);
			if (list.contains(whiteKingPosition)) {
				controller.whiteTurn = !controller.whiteTurn;
				controller.board.flipBoard(chessBoard);
				return false;
			}
		} else {
			list += controller.movementManager.generateAllMoves(chessBoard,
					Colors.WHITE, controller);
			if (list.contains(blackKingPosition)) {
				controller.whiteTurn = !controller.whiteTurn;
				controller.board.flipBoard(chessBoard);
				return false;
			}
		}
		// controller.whiteTurn = !controller.whiteTurn;
		controller.board.flipBoard(chessBoard);
		System.out.println(whiteKingPosition);

		return true;
	}
}
