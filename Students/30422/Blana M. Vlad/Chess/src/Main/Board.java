package Main;

import pieces.Bishop;
import pieces.Colors;
import pieces.King;
import pieces.Knight;
import pieces.NoPiece;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board {
	private Piece[][] chessBoard = new Piece[8][8];
	public Piece[][] auxChessBoard = new Piece[8][8];

	public Board() {
		int i, j;
		for (j = 0; j < 8; j++) {
			chessBoard[1][j] = new Pawn();
			chessBoard[1][j].setColor(Colors.BLACK);
			chessBoard[6][j] = new Pawn();
			chessBoard[6][j].setColor(Colors.WHITE);
		}
		for (i = 2; i < 6; i++) {
			for (j = 0; j < 8; j++) {
				chessBoard[i][j] = new NoPiece();
				chessBoard[i][j].setColor(Colors.WHITE);
			}
		}
		chessBoard[0][0] = new Rook();
		chessBoard[0][0].setColor(Colors.BLACK);

		chessBoard[0][1] = new Knight();
		chessBoard[0][1].setColor(Colors.BLACK);

		chessBoard[0][2] = new Bishop();
		chessBoard[0][2].setColor(Colors.BLACK);
		chessBoard[0][3] = new Queen();
		chessBoard[0][3].setColor(Colors.BLACK);
		chessBoard[0][4] = new King();
		chessBoard[0][4].setColor(Colors.BLACK);
		chessBoard[0][5] = new Bishop();
		chessBoard[0][5].setColor(Colors.BLACK);
		chessBoard[0][6] = new Knight();
		chessBoard[0][6].setColor(Colors.BLACK);
		chessBoard[0][7] = new Rook();
		chessBoard[0][7].setColor(Colors.BLACK);
		// Have to do it like this..
		chessBoard[7][0] = new Rook();
		chessBoard[7][0].setColor(Colors.WHITE);
		chessBoard[7][1] = new Knight();
		chessBoard[7][1].setColor(Colors.WHITE);
		chessBoard[7][2] = new Bishop();
		chessBoard[7][2].setColor(Colors.WHITE);
		chessBoard[7][3] = new Queen();
		chessBoard[7][3].setColor(Colors.WHITE);
		chessBoard[7][4] = new King();
		chessBoard[7][4].setColor(Colors.WHITE);
		chessBoard[7][5] = new Bishop();
		chessBoard[7][5].setColor(Colors.WHITE);
		chessBoard[7][6] = new Knight();
		chessBoard[7][6].setColor(Colors.WHITE);
		chessBoard[7][7] = new Rook();
		chessBoard[7][7].setColor(Colors.WHITE);
	}

	public Piece[][] getBoard() {
		return chessBoard;
	}

	public void setBoard(Piece[][] chessB) {
		chessBoard = chessB;
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
