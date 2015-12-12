package Pieces;

import Main.MainChess;

public class Queen extends Piece {
	@Override
	public ListOfPieces getType() {
		return ListOfPieces.QUEEN;
	}

	@Override
	public String possibleMove(int r, int c) {
		Piece[][] chessBoard = MainChess.board.getBoard();
		String list = "";
		Piece oldPiece;
		for (int i = 0; i < 4; i++) {
			int rowOffset = 0, columnOffset = 0;
			switch (i) {
			case 0:
				rowOffset = -1;
				columnOffset = 1;// move right
				break;
			case 1:
				rowOffset = 1;
				columnOffset = 1;// move left
				break;
			case 2:
				rowOffset = -1;
				columnOffset = -1;// move up
				break;
			case 3:
				rowOffset = 1;
				columnOffset = -1;// move down
				break;
			}
			try {// move
				while (chessBoard[r + rowOffset][c + columnOffset].getType() == ListOfPieces.NOPIECE) {
					oldPiece = chessBoard[r + rowOffset][c + columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = chessBoard[r][c];
					chessBoard[r][c] = MainChess.emptySpace;

					if (MainChess.kingSafety() == true) {
						list = list + r + c + (r + rowOffset)
								+ (c + columnOffset) + oldPiece;
					}
					chessBoard[r][c] = chessBoard[r + rowOffset][c
							+ columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = oldPiece;

					switch (i) {
					case 0:
						rowOffset--;
						columnOffset++;
						break;
					case 1:
						rowOffset++;
						columnOffset++;
						break;
					case 2:
						rowOffset--;
						columnOffset--;
						break;
					case 3:
						rowOffset++;
						columnOffset--;
						break;
					}
				}
				if (chessBoard[r + rowOffset][c + columnOffset].getColor() != chessBoard[r][c]
						.getColor()) {
					oldPiece = chessBoard[r + rowOffset][c + columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = chessBoard[r][c];
					chessBoard[r][c] = MainChess.emptySpace;

					if (MainChess.kingSafety() == true) {
						list = list + r + c + (r + rowOffset)
								+ (c + columnOffset) + oldPiece;
					}
					chessBoard[r][c] = chessBoard[r + rowOffset][c
							+ columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = oldPiece;

				}
			} catch (Exception e) {
			}
		}
		for (int i = 0; i < 4; i++) {
			int rowOffset = 0, columnOffset = 0;
			switch (i) {
			case 0:
				rowOffset = 0;
				columnOffset = 1;// move right
				break;
			case 1:
				rowOffset = 0;
				columnOffset = -1;// move left
				break;
			case 2:
				rowOffset = -1;
				columnOffset = 0;// move up
				break;
			case 3:
				rowOffset = 1;
				columnOffset = 0;// move down
				break;
			}
			try {// move
				while (chessBoard[r + rowOffset][c + columnOffset].getType() == ListOfPieces.NOPIECE) {
					oldPiece = chessBoard[r + rowOffset][c + columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = chessBoard[r][c];
					chessBoard[r][c] = MainChess.emptySpace;

					if (MainChess.kingSafety() == true) {
						list = list + r + c + (r + rowOffset)
								+ (c + columnOffset) + oldPiece;
					}
					chessBoard[r][c] = chessBoard[r + rowOffset][c
							+ columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = oldPiece;

					switch (i) {
					case 0:
						columnOffset++;
						break;
					case 1:
						columnOffset--;
						break;
					case 2:
						rowOffset--;
						break;
					case 3:
						rowOffset++;
						break;
					}
				}
				if (chessBoard[r + rowOffset][c + columnOffset].getColor() != chessBoard[r][c]
						.getColor()) {
					oldPiece = chessBoard[r + rowOffset][c + columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = chessBoard[r][c];
					chessBoard[r][c] = MainChess.emptySpace;

					if (MainChess.kingSafety() == true) {
						list = list + r + c + (r + rowOffset)
								+ (c + columnOffset) + oldPiece;
					}
					chessBoard[r][c] = chessBoard[r + rowOffset][c
							+ columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = oldPiece;

				}
			} catch (Exception e) {
			}
		}
		return list;
	}
}
