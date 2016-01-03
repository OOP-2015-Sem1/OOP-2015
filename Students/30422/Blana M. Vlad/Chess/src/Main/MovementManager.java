package Main;

import java.util.ArrayList;
import java.util.List;

import pieces.Colors;
import pieces.Piece;
import pieces.Pieces;
import pieces.Queen;

public class MovementManager {

	public void makeMove(Movement move, Piece[][] chessBoard,
			Controller controller) {

		if (castleManager(move, chessBoard, controller) == false) {
			//
			chessBoard[move.destination.x][move.destination.y] = chessBoard[move.source.x][move.source.y];
			chessBoard[move.source.x][move.source.y] = controller.emptySpace;
		}

		//
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

	public List<Movement> generateAllMoves(Piece[][] chessBoard, Colors color,
			Controller controller, boolean checkMate) {
		List<Movement> list = new ArrayList<Movement>();
		List<Movement> templist = new ArrayList<Movement>();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (chessBoard[i][j].getColor() == color)
					templist = chessBoard[i][j].possibleMove(i, j, chessBoard,
							checkMate, controller);
				for (Movement element : templist) {
					list.add(element);
				}
			}
		}
		return list;
	}

	public Boolean checkValidMove(Movement move, Controller controller) {
		Piece[][] chessBoard = controller.board.getBoard();
		List<Movement> list = new ArrayList<Movement>();
		list = (chessBoard[move.source.x][move.source.y].possibleMove(
				move.source.x, move.source.y, chessBoard, true, controller));
		// System.out.println(list);

		System.out.println();
		for (Movement move2 : list) {
			if (move.equals(move2)) {
				return true;
			}
		}
		return false;

	}

	public boolean castleManager(Movement move, Piece[][] chessBoard,
			Controller controller) {

		if (chessBoard[move.source.x][move.source.y].getType() == Pieces.KING) {
			if (chessBoard[move.source.x][move.source.y].getColor() == Colors.WHITE) {
				controller.enableLeftWhiteCastle = false;
				controller.enableRightWhiteCastle = false;
			} else {
				controller.enableLeftBlackCastle = false;
				controller.enableRightBlackCastle = false;
			}
		}
		if (chessBoard[move.source.x][move.source.y].getType() == Pieces.ROOK) {
			if (chessBoard[move.source.x][move.source.y].getColor() == Colors.WHITE) {
				if (move.source.x == 7 && move.source.y == 0) {
					controller.enableLeftWhiteCastle = false;
				}
				if (move.source.x == 7 && move.source.y == 7) {
					controller.enableRightWhiteCastle = false;
				}
			} else {
				if (move.source.x == 7 && move.source.y == 0) {

					controller.enableLeftBlackCastle = false;
				}
				if (move.source.x == 7 && move.source.y == 7) {

					controller.enableRightBlackCastle = false;
				}
			}
		}

		if (move.source.x == 7 && move.source.y == 4 && move.destination.x == 7
				&& move.destination.y == 2
				&& chessBoard[7][4].getType() == Pieces.KING) {
			chessBoard[7][2] = chessBoard[7][4];
			chessBoard[7][4] = controller.emptySpace;

			chessBoard[7][3] = chessBoard[7][0];
			chessBoard[7][0] = controller.emptySpace;
			return true;

		} else {
			if (move.source.x == 7 && move.source.y == 4
					&& move.destination.x == 7 && move.destination.y == 6
					&& chessBoard[7][4].getType() == Pieces.KING) {
				chessBoard[7][6] = chessBoard[7][4];
				chessBoard[7][4] = controller.emptySpace;

				chessBoard[7][5] = chessBoard[7][7];
				chessBoard[7][7] = controller.emptySpace;
				return true;
			} else {
				if (move.source.x == 7 && move.source.y == 3
						&& move.destination.x == 7 && move.destination.y == 1
						&& chessBoard[7][3].getType() == Pieces.KING) {
					chessBoard[7][1] = chessBoard[7][3];
					chessBoard[7][3] = controller.emptySpace;

					chessBoard[7][2] = chessBoard[7][0];
					chessBoard[7][0] = controller.emptySpace;
					return true;
				} else {
					if (move.source.x == 7 && move.source.y == 3
							&& move.destination.x == 7
							&& move.destination.y == 5
							&& chessBoard[7][3].getType() == Pieces.KING) {
						chessBoard[7][5] = chessBoard[7][3];
						chessBoard[7][3] = controller.emptySpace;

						chessBoard[7][4] = chessBoard[7][7];
						chessBoard[7][7] = controller.emptySpace;
						return true;
					}

				}
			}
		}
		return false;
	}
}
