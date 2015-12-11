package Pieces;

import Main.Board;
import Main.MainChess;

public class Pawn extends Piece {
	public static Piece[][] chessBoard = Board.getBoard();

	@Override
	public String possibleMove(int r, int c) {
		String list = "";
		Piece oldPiece = new Piece();
		try {// move one up
			if (" ".equals(chessBoard[r - 1][c])) {
				oldPiece = chessBoard[r - 1][c];
				Board.exchange(chessBoard[r][c], chessBoard[r - 1][c]);
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 1) + c + oldPiece;
				}
				chessBoard[r][c] = chessBoard[r - 1][c];
				chessBoard[r - 1][c] = oldPiece;
			}
		} catch (Exception e) {
		}
		try {// promote
			if (" ".equals(chessBoard[r - 1][c]) && r == 1) {
				oldPiece = chessBoard[r - 1][c];
				chessBoard[r - 1][c] = new Queen();
				chessBoard[r][c] = new Piece();// look
												// into
												// it
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 1) + c + oldPiece;
				}
				chessBoard[r][c] = new Pawn();
				chessBoard[r - 1][c] = oldPiece;
			}
		} catch (Exception e) {
		}
		try {// move two up
			if (" ".equals(chessBoard[r - 2][c]) && r == 6) {
				oldPiece = chessBoard[r - 2][c];
				Board.exchange(chessBoard[r][c], chessBoard[r - 2][c]);
				if (MainChess.kingSafety() == true) {
					list = list + r + c + (r - 2) + c + oldPiece;
				}
				chessBoard[r][c] = chessBoard[r - 2][c];
				chessBoard[r - 2][c] = oldPiece;
			}
		} catch (Exception e) {
		}
		/*
		 * try {// capture right
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