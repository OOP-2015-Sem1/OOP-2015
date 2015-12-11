package Pieces;

import Main.MainChess;

public class Pawn extends Piece {

	@Override
	public String possibleMove(int r, int c) {
		Piece[][] chessBoard = MainChess.board.getBoard();
		String list = "";
		Piece oldPiece = new Piece();
		try {// move one up
			if ((chessBoard[r - 1][c]).getType() == ListOfPieces.NOPIECE) {
				// oldPiece = chessBoard[r - 1][c];

				chessBoard[r - 1][c] = new Pawn();
				chessBoard[r - 1][c].setColor(Colors.WHITE);
				chessBoard[r][c] = MainChess.emptySpace;
				// Board.exchange(chessBoard[r][c], chessBoard[r - 1][c]);
				// System.out.println(chessBoard[r - 1][c].getType());
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 1) + c + " ";
				}

				chessBoard[r][c] = new Pawn();
				chessBoard[r - 1][c] = MainChess.emptySpace;

				MainChess.board.setBoard(chessBoard);
			}
		} catch (Exception e) {
		}
		/*
		 * try {// promote if ((chessBoard[r - 1][c]).getType() ==
		 * ListOfPieces.NOPIECE && r == 1) { oldPiece = chessBoard[r - 1][c];
		 * chessBoard[r - 1][c] = new Queen(); chessBoard[r][c] =
		 * MainChess.emptySpace; if (MainChess.kingSafety() == true) { list =
		 * list + r + c + (r - 1) + c + " "; } chessBoard[r][c] = chessBoard[r -
		 * 1][c]; chessBoard[r - 1][c] = oldPiece;
		 * MainChess.board.setBoard(chessBoard); } } catch (Exception e) { } try
		 * {// move two up if ((chessBoard[r - 2][c]).getType() ==
		 * ListOfPieces.NOPIECE && r == 6) { oldPiece = chessBoard[r - 2][c];
		 * Board.exchange(chessBoard[r][c], chessBoard[r - 2][c]); if
		 * (MainChess.kingSafety() == true) { list = list + r + c + (r - 2) + c
		 * + " "; } chessBoard[r][c] = chessBoard[r - 2][c]; chessBoard[r -
		 * 2][c] = oldPiece; MainChess.board.setBoard(chessBoard); } } catch
		 * (Exception e) { } /* try {// capture right
		 * 
		 * if (" ".charAt(0) != chessBoard[r - 1][c + 1].charAt(0) &&
		 * "PTCNQK".contains(chessBoard[r - 1][c + 1]) == false) { oldPiece =
		 * chessBoard[r - 1][c + 1]; chessBoard[r][c] = null; chessBoard[r -
		 * 1][c + 1] = new Pawn(); if (MainChess.kingSafety() == true) { list =
		 * list + r + c + (r - 1) + (c + 1) + oldPiece; } chessBoard[r][c] = new
		 * Pawn(); chessBoard[r - 1][c + 1] = oldPiece; } } catch (Exception e)
		 * { } try {// capture left if (" ".charAt(0) != chessBoard[r - 1][c -
		 * 1].charAt(0) && "PTCNQK".contains(chessBoard[r - 1][c - 1]) == false)
		 * { oldPiece = chessBoard[r - 1][c - 1]; chessBoard[r][c] = null;
		 * chessBoard[r - 1][c - 1] = new Pawn(); if (MainChess.kingSafety() ==
		 * true) { list = list + r + c + (r - 1) + (c - 1) + oldPiece; }
		 * chessBoard[r][c] = new Pawn(); chessBoard[r - 1][c - 1] = oldPiece; }
		 * } catch (Exception e) { } try {// capture and promote right
		 * 
		 * if (" ".charAt(0) != chessBoard[r - 1][c + 1].charAt(0) &&
		 * "PTCNQK".contains(chessBoard[r - 1][c + 1]) == false && r == 1) {
		 * oldPiece = chessBoard[r - 1][c + 1]; chessBoard[r][c] = null;
		 * chessBoard[r - 1][c + 1] = new Pawn(); if (MainChess.kingSafety() ==
		 * true) { list = list + r + c + (r - 1) + (c + 1) + oldPiece; }
		 * chessBoard[r][c] = new Pawn(); chessBoard[r - 1][c + 1] = oldPiece; }
		 * } catch (Exception e) { } try {// capture and promote left if
		 * (" ".charAt(0) != chessBoard[r - 1][c - 1].charAt(0) &&
		 * "PTCNQK".contains(chessBoard[r - 1][c - 1]) == false && r == 1) {
		 * oldPiece = chessBoard[r - 1][c - 1]; chessBoard[r][c] = null;
		 * chessBoard[r - 1][c - 1] = new Pawn(); if (MainChess.kingSafety() ==
		 * true) { list = list + r + c + (r - 1) + (c - 1) + oldPiece; }
		 * chessBoard[r][c] = new Pawn(); chessBoard[r - 1][c - 1] = oldPiece; }
		 * } catch (Exception e) { }
		 */

		return list;

	}

	@Override
	public ListOfPieces getType() {
		return ListOfPieces.PAWN;
	}
}
