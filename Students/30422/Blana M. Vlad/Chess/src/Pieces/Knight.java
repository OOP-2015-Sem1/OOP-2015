package Pieces;

import Main.MainChess;

public class Knight extends Piece {
	@Override
	public ListOfPieces getType() {
		return ListOfPieces.KNIGHT;
	}

	@Override
	public String possibleMove(int r, int c) { // CAL
		Piece[][] chessBoard = MainChess.board.getBoard();
		String list = "";
		Piece oldPiece;
		for (int i = 0; i < 8; i++) {
			int rowOffset = 0, columnOffset = 0;// row offset, column offset
			switch (i) {
			case 0:
				rowOffset = -2;
				columnOffset = 1;
				break;
			case 1:
				rowOffset = -2;
				columnOffset = -1;
				break;
			case 2:
				rowOffset = -1;
				columnOffset = 2;
				break;
			case 3:
				rowOffset = -1;
				columnOffset = -2;
				break;
			case 4:
				rowOffset = 1;
				columnOffset = -2;
				break;
			case 5:
				rowOffset = 1;
				columnOffset = 2;
				break;
			case 6:
				rowOffset = 2;
				columnOffset = -1;
				break;
			case 7:
				rowOffset = 2;
				columnOffset = 1;
				break;
			}
			try {// move
				if ((chessBoard[r + rowOffset][c + columnOffset]).getType() == ListOfPieces.NOPIECE
						|| chessBoard[r + rowOffset][c + columnOffset]
								.getColor() != chessBoard[r][c].getColor()) {
					oldPiece = chessBoard[r + rowOffset][c + columnOffset];
					chessBoard[r + rowOffset][c + columnOffset] = chessBoard[r][c];
					chessBoard[r][c] = MainChess.emptySpace;

					if (MainChess.kingSafety() == true) {
						list = list + r + c + (r + rowOffset)
								+ (c + columnOffset) + " ";
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
