package Main;

import pieces.Colors;
import pieces.Piece;

public class Restrictions {

	public static boolean kingSafety(Piece[][] chessBoard, Colors color) {
		String list = "";
		String whiteKingPosition = MainChess.whiteKingX + MainChess.whiteKingY
				+ "p";
		String blackKingPosition = MainChess.blackKingX + MainChess.blackKingY
				+ "p";
		MainChess.board.flipBoard(chessBoard);
		// MainChess.whiteTurn = !MainChess.whiteTurn;
		if (color == Colors.WHITE) {
			list += Movement.generateAllMoves(chessBoard, Colors.BLACK);
			if (list.contains(whiteKingPosition)) {
				// MainChess.whiteTurn = !MainChess.whiteTurn;
				MainChess.board.flipBoard(chessBoard);
				return false;
			}
		} else {
			list += Movement.generateAllMoves(chessBoard, Colors.WHITE);
			if (list.contains(blackKingPosition)) {
				MainChess.whiteTurn = !MainChess.whiteTurn;
				MainChess.board.flipBoard(chessBoard);
				return false;
			}
		}
		// MainChess.whiteTurn = !MainChess.whiteTurn;
		MainChess.board.flipBoard(chessBoard);
		System.out.println(whiteKingPosition);

		return true;
	}
}
