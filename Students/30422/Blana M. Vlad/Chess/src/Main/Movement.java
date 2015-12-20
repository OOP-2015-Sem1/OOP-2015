package Main;

import pieces.Colors;
import pieces.Piece;
import pieces.Pieces;
import pieces.Queen;

public class Movement {
	public static void makeMove(String move, Piece[][] chessBoard) {
		chessBoard[Character.getNumericValue(move.charAt(2))][Character
				.getNumericValue(move.charAt(3))] = chessBoard[Character
				.getNumericValue(move.charAt(0))][Character
				.getNumericValue(move.charAt(1))];

		chessBoard[Character.getNumericValue(move.charAt(0))][Character
				.getNumericValue(move.charAt(1))] = MainChess.emptySpace;

		for (int j = 0; j <= 7; j++) {
			if (chessBoard[0][j].getType() == Pieces.PAWN) {
				chessBoard[0][j] = new Queen();
				if (MainChess.whiteTurn == true) {
					chessBoard[0][j].setColor(Colors.WHITE);
				} else {
					chessBoard[0][j].setColor(Colors.BLACK);
				}
			}
		}

		System.out.println();
		MainChess.board.setBoard(chessBoard);
	}

	public static String generateAllMoves(Piece[][] chessBoard, Colors color) {
		String list = "";
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (chessBoard[i][j].getColor() == color)
					list += chessBoard[i][j].possibleMove(i, j, chessBoard);
			}
		}
		return list;
	}

	public static Boolean checkValidMove(String move) {
		Piece[][] chessBoard = MainChess.board.getBoard();
		String list = "";
		list += chessBoard[Character.getNumericValue(move.charAt(0))][Character
				.getNumericValue(move.charAt(1))].possibleMove(
				Character.getNumericValue(move.charAt(0)),
				Character.getNumericValue(move.charAt(1)), chessBoard);

		if (list.replaceAll(move, "").length() < list.length()) {
			return true;
		} else
			return false;
	}

}
