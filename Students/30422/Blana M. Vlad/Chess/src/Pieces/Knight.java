package pieces;

import java.util.ArrayList;
import java.util.List;

import Main.Controller;
import Main.Movement;
import Main.Restrictions;

public class Knight extends Piece {
	@Override
	public Pieces getType() {
		return Pieces.KNIGHT;
	}

	@Override
	public List<Movement> possibleMove(int row, int column,
			Piece chessBoard[][], boolean checkKingSafety, Controller controller) { // CAL
		// Piece[][] chessBoard = MainChess.board.getBoard();
		List<Movement> list = new ArrayList<Movement>();
		Movement move;
		Piece oldPiece;
		Colors currentColor;
		if (controller.whiteTurn == true) {
			currentColor = Colors.WHITE;
		} else {
			currentColor = Colors.BLACK;
		}
		if (chessBoard[row][column].getColor() == currentColor) {
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
					if ((chessBoard[row + rowOffset][column + columnOffset])
							.getType() == Pieces.NOPIECE
							|| chessBoard[row + rowOffset][column
									+ columnOffset].getColor() != chessBoard[row][column]
									.getColor()) {
						oldPiece = chessBoard[row + rowOffset][column
								+ columnOffset];
						chessBoard[row + rowOffset][column + columnOffset] = chessBoard[row][column];
						chessBoard[row][column] = controller.emptySpace;
						if (checkKingSafety == true) {
							if (Restrictions.kingSafety(chessBoard,
									currentColor, controller) == true) {
								move = new Movement();
								move.setMove(row, column, row + rowOffset,
										column + columnOffset, true);
								list.add(move);
							}
						} else {
							move = new Movement();
							move.setMove(row, column, row + rowOffset, column
									+ columnOffset, true);
							list.add(move);
						}
						chessBoard[row][column] = chessBoard[row + rowOffset][column
								+ columnOffset];
						chessBoard[row + rowOffset][column + columnOffset] = oldPiece;
					}

				} catch (Exception e) {
				}
			}
		}
		return list;
	}
}
