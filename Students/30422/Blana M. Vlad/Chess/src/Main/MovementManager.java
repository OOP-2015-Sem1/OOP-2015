package Main;

import pieces.Colors;
import pieces.Piece;
import pieces.Pieces;
import pieces.Queen;

public class MovementManager {
	public void makeMove(Movement move, Piece[][] chessBoard,
			Controller controller) {
		chessBoard[move.destination.x][move.destination.y] = chessBoard[move.source.x][move.source.y];

		chessBoard[move.source.x][move.source.y] = controller.emptySpace;

		for (int j = 0; j <= 7; j++) {
			if (chessBoard[0][j].getType() == Pieces.PAWN) {
				chessBoard[0][j] = new Queen();
				if (controller.whiteTurn == true) {
					chessBoard[0][j].setColor(Colors.WHITE);
				} else {
					chessBoard[0][j].setColor(Colors.BLACK);
				}
			}
		}

		System.out.println();
		controller.board.setBoard(chessBoard);
	}

	public String generateAllMoves(Piece[][] chessBoard, Colors color,
			Controller controller) {
		String list = "";
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (chessBoard[i][j].getColor() == color)
					list += chessBoard[i][j].possibleMove(i, j, chessBoard,
							false, controller);
			}
		}
		return list;
	}

	public Boolean checkValidMove(Movement move, Controller controller) {
		Piece[][] chessBoard = controller.board.getBoard();
		String list = "";
		list += chessBoard[move.source.x][move.source.y].possibleMove(
				move.source.x, move.source.y, chessBoard, true, controller);

		if (list.replaceAll(move.encodeMoveToString(), "").length() < list
				.length()) {
			return true;
		} else
			return false;
	}
}
