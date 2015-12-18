package Pieces;

import Main.MainChess;

public class Pawn extends Piece {

	@Override
	public String possibleMove(int r, int c) {
		Piece[][] chessBoard = MainChess.board.getBoard();
		String list = "";
		Colors currentColor;
		Piece oldPiece = new Piece();
		if (MainChess.whiteTurn == true) {
			oldPiece.setColor(Colors.BLACK);
			currentColor = Colors.WHITE;
		} else {
			oldPiece.setColor(Colors.WHITE);
			currentColor = Colors.BLACK;
		}
		try {// move one up
			if ((chessBoard[r - 1][c]).getType() == ListOfPieces.NOPIECE) {
				chessBoard[r - 1][c] = chessBoard[r][c];
				chessBoard[r][c] = MainChess.emptySpace;
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 1) + c + " ";
				}
				chessBoard[r][c] = chessBoard[r - 1][c];
				chessBoard[r - 1][c] = MainChess.emptySpace;

			}
		} catch (Exception e) {
		}
		try {// move two up
			if ((chessBoard[r - 2][c]).getType() == ListOfPieces.NOPIECE
					&& r == 6
					&& chessBoard[r - 1][c].getType() == ListOfPieces.NOPIECE) {
				chessBoard[r - 2][c] = chessBoard[r][c];
				chessBoard[r][c] = MainChess.emptySpace;
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 2) + c + " ";
				}
				chessBoard[r][c] = chessBoard[r - 2][c];
				chessBoard[r - 2][c] = MainChess.emptySpace;

			}
		} catch (Exception e) {
		}
		try {// capture right
			if (chessBoard[r - 1][c + 1].getType() != ListOfPieces.NOPIECE
					&& chessBoard[r - 1][c + 1].getColor() != currentColor
					&& chessBoard[r][c].getColor() == currentColor) {
				oldPiece = chessBoard[r - 1][c + 1];
				chessBoard[r - 1][c + 1] = chessBoard[r][c];
				chessBoard[r][c] = MainChess.emptySpace;
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 1) + (c + 1) + "p";
				}

				chessBoard[r][c] = chessBoard[r - 1][c + 1];
				chessBoard[r - 1][c + 1] = oldPiece;
			}
		} catch (Exception e) {
		}

		try {// capture left
			if (chessBoard[r - 1][c - 1].getType() != ListOfPieces.NOPIECE
					&& chessBoard[r - 1][c - 1].getColor() != currentColor
					&& chessBoard[r][c].getColor() == currentColor) {
				oldPiece = chessBoard[r - 1][c - 1];
				chessBoard[r - 1][c - 1] = chessBoard[r][c];
				chessBoard[r][c] = MainChess.emptySpace;
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 1) + (c - 1) + "p";
				}
				chessBoard[r][c] = chessBoard[r - 1][c - 1];
				chessBoard[r - 1][c - 1] = oldPiece;
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public ListOfPieces getType() {
		return ListOfPieces.PAWN;
	}
}
