package pieces;

import Main.Controller;
import Main.Restrictions;

public class Pawn extends Piece {

	@Override
	public String possibleMove(int row, int column, Piece chessBoard[][],
			boolean checkKingSafety, Controller controller) {
		// Piece[][] chessBoard = MainChess.board.getBoard();
		String list = "";
		Piece oldPiece = new NoPiece();
		Colors currentColor;
		if (controller.whiteTurn == true) {
			currentColor = Colors.WHITE;
		} else {
			currentColor = Colors.BLACK;
		}
		if (chessBoard[row][column].getColor() == currentColor) {
			try {// move one up
				if ((chessBoard[row - 1][column]).getType() == Pieces.NOPIECE) {
					chessBoard[row - 1][column] = chessBoard[row][column];
					chessBoard[row][column] = controller.emptySpace;
					if (Restrictions.kingSafety(chessBoard, currentColor,
							controller) == true) {
						list = list + row + column + (row - 1) + column + " ";
					}
					chessBoard[row][column] = chessBoard[row - 1][column];
					chessBoard[row - 1][column] = controller.emptySpace;

				}
			} catch (Exception e) {
			}
			try {// move two up
				if ((chessBoard[row - 2][column]).getType() == Pieces.NOPIECE
						&& row == 6
						&& chessBoard[row - 1][column].getType() == Pieces.NOPIECE) {
					chessBoard[row - 2][column] = chessBoard[row][column];
					chessBoard[row][column] = controller.emptySpace;
					if (Restrictions.kingSafety(chessBoard, currentColor,
							controller) == true) {
						list = list + row + column + (row - 2) + column + " ";
					}
					chessBoard[row][column] = chessBoard[row - 2][column];
					chessBoard[row - 2][column] = controller.emptySpace;

				}
			} catch (Exception e) {
			}
			try {// capture right
				if (chessBoard[row - 1][column + 1].getType() != Pieces.NOPIECE
						&& chessBoard[row - 1][column + 1].getColor() != currentColor
						&& chessBoard[row][column].getColor() == currentColor) {
					oldPiece = chessBoard[row - 1][column + 1];
					chessBoard[row - 1][column + 1] = chessBoard[row][column];
					chessBoard[row][column] = controller.emptySpace;
					if (Restrictions.kingSafety(chessBoard, currentColor,
							controller) == true) {
						list = list + row + column + (row - 1) + (column + 1)
								+ "p";
					}

					chessBoard[row][column] = chessBoard[row - 1][column + 1];
					chessBoard[row - 1][column + 1] = oldPiece;
				}
			} catch (Exception e) {
			}

			try {// capture left
				if (chessBoard[row - 1][column - 1].getType() != Pieces.NOPIECE
						&& chessBoard[row - 1][column - 1].getColor() != currentColor
						&& chessBoard[row][column].getColor() == currentColor) {
					oldPiece = chessBoard[row - 1][column - 1];
					chessBoard[row - 1][column - 1] = chessBoard[row][column];
					chessBoard[row][column] = controller.emptySpace;
					if (Restrictions.kingSafety(chessBoard, currentColor,
							controller) == true) {
						list = list + row + column + (row - 1) + (column - 1)
								+ "p";
					}
					chessBoard[row][column] = chessBoard[row - 1][column - 1];
					chessBoard[row - 1][column - 1] = oldPiece;
				}
			} catch (Exception e) {
			}
		}
		return list;
	}

	@Override
	public Pieces getType() {
		return Pieces.PAWN;
	}
}
