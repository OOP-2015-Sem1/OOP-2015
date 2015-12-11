package Main;

import Pieces.Bishop;
import Pieces.Colors;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;

public class Board {
	public static Piece[][] chessBoard;
	public static Piece[][] auxChessBoard = new Piece[8][8];

	public static void exchange(Piece piece1, Piece piece2) {
		Piece aux = piece1;
		piece1 = piece2;
		piece2 = aux;
	}

	public Board(Piece[][] chessBoard) {
		int i, j;
		for (j = 0; j < 8; j++) {
			chessBoard[1][j] = new Pawn();
			chessBoard[1][j].setColor(Colors.WHITE);
			chessBoard[6][j] = new Pawn();
			chessBoard[6][j].setColor(Colors.BLACK);
		}
		for (i = 2; i < 6; i++) {
			for (j = 2; j < 6; j++) {
				chessBoard[i][j] = new Piece();
			}
		}
		chessBoard[0][0] = new Rook();
		chessBoard[0][0].setColor(Colors.WHITE);
		chessBoard[0][1] = new Knight();
		chessBoard[0][0].setColor(Colors.WHITE);
		chessBoard[0][2] = new Bishop();
		chessBoard[0][0].setColor(Colors.WHITE);
		chessBoard[0][3] = new Queen();
		chessBoard[0][0].setColor(Colors.WHITE);
		chessBoard[0][4] = new King();
		chessBoard[0][0].setColor(Colors.WHITE);
		chessBoard[0][5] = new Bishop();
		chessBoard[0][0].setColor(Colors.WHITE);
		chessBoard[0][6] = new Knight();
		chessBoard[0][0].setColor(Colors.WHITE);
		chessBoard[0][7] = new Rook();
		chessBoard[0][0].setColor(Colors.WHITE);
		// Have to do it like this..
		chessBoard[7][0] = new Rook();
		chessBoard[7][0].setColor(Colors.BLACK);
		chessBoard[7][1] = new Knight();
		chessBoard[7][0].setColor(Colors.BLACK);
		chessBoard[7][2] = new Bishop();
		chessBoard[7][0].setColor(Colors.BLACK);
		chessBoard[7][3] = new Queen();
		chessBoard[7][0].setColor(Colors.BLACK);
		chessBoard[7][4] = new King();
		chessBoard[7][0].setColor(Colors.BLACK);
		chessBoard[7][5] = new Bishop();
		chessBoard[7][0].setColor(Colors.BLACK);
		chessBoard[7][6] = new Knight();
		chessBoard[7][0].setColor(Colors.BLACK);
		chessBoard[7][7] = new Rook();
		chessBoard[7][0].setColor(Colors.BLACK);
		this.chessBoard = chessBoard;
	}

	public static Piece[][] getBoard() {
		return chessBoard;
	}

	public void flipBoard(Piece[][] chessBoard) {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				auxChessBoard[i][j] = chessBoard[i][j];
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoard[i][j] = auxChessBoard[7 - i][7 - j];
			}
		}
	}
}
