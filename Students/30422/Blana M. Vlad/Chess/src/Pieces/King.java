package pieces;

import java.util.ArrayList;
import java.util.List;

import Main.Controller;
import Main.Movement;
import Main.Restrictions;

public class King extends Piece {
	@Override
	public Pieces getType() {
		return Pieces.KING;
	}

	@Override
	public List<Movement> possibleMove(int row, int column,
			Piece chessBoard[][], boolean checkKingSafety, Controller controller) {
		// Piece[][] chessBoard = MainChess.board.getBoard();
		boolean finalCondition;
		List<Movement> list = new ArrayList<Movement>();
		Movement move = new Movement();
		Piece oldPiece;
		Colors currentColor;
		if (controller.whiteTurn == true) {
			currentColor = Colors.WHITE;
		} else {
			currentColor = Colors.BLACK;
		}
		if (chessBoard[row][column].getColor() == currentColor) {
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
					if (chessBoard[row + rowOffset][column + columnOffset]
							.getType() == Pieces.NOPIECE) {
						oldPiece = chessBoard[row + rowOffset][column
								+ columnOffset];
						chessBoard[row + rowOffset][column + columnOffset] = chessBoard[row][column];
						chessBoard[row][column] = controller.emptySpace;
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
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
						//
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
						//
					}
					if (chessBoard[row + rowOffset][column + columnOffset]
							.getColor() != chessBoard[row][column].getColor()) {
						oldPiece = chessBoard[row + rowOffset][column
								+ columnOffset];
						chessBoard[row + rowOffset][column + columnOffset] = chessBoard[row][column];
						chessBoard[row][column] = controller.emptySpace;
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
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
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
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
					if (chessBoard[row + rowOffset][column + columnOffset]
							.getType() == Pieces.NOPIECE) {
						oldPiece = chessBoard[row + rowOffset][column
								+ columnOffset];
						chessBoard[row + rowOffset][column + columnOffset] = chessBoard[row][column];
						chessBoard[row][column] = controller.emptySpace;
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
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
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
					}
					if (chessBoard[row + rowOffset][column + columnOffset]
							.getColor() != chessBoard[row][column].getColor()) {
						oldPiece = chessBoard[row + rowOffset][column
								+ columnOffset];
						chessBoard[row + rowOffset][column + columnOffset] = chessBoard[row][column];
						chessBoard[row][column] = controller.emptySpace;
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
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
						controller.board.flipBoard(chessBoard);
						for (int j = 0; j < 8; j++) {
							for (int k = 0; k < 8; k++) {
								if (chessBoard[j][k].getType() == Pieces.KING
										&& chessBoard[j][k].getColor() == currentColor) {
									if (currentColor == Colors.WHITE) {
										controller.whiteKingPosition.setMove(9,
												9, j, k, false);
									} else {
										controller.blackKingPosition.setMove(9,
												9, j, k, false);
									}

								}
							}
						}
						controller.board.flipBoard(chessBoard);
					}
				} catch (Exception e) {
				}
			}
		}
		if (currentColor == Colors.WHITE) {

			if (controller.enableLeftWhiteCastle == true) {
				try {// castle left white
					finalCondition = (Restrictions.checkSquareSafety(0, 3,
							Colors.WHITE, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 4,
									Colors.WHITE, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 5,
									Colors.WHITE, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 6,
									Colors.WHITE, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 7,
									Colors.WHITE, chessBoard, controller)
							&& chessBoard[7][1] == controller.emptySpace
							&& chessBoard[7][2] == controller.emptySpace && chessBoard[7][3] == controller.emptySpace);
					if (finalCondition == true) {
						move = new Movement();
						move.setMove(row, column, 7, 2, false);
						list.add(move);
					}
				} catch (Exception e) {
				}
			}
			if (controller.enableRightWhiteCastle == true) {
				try {// castle right white

					finalCondition = (Restrictions.checkSquareSafety(0, 0,
							Colors.WHITE, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 1,
									Colors.WHITE, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 2,
									Colors.WHITE, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 3,
									Colors.WHITE, chessBoard, controller)
							&& chessBoard[7][5] == controller.emptySpace && chessBoard[7][6] == controller.emptySpace);
					if (finalCondition == true) {
						move = new Movement();
						move.setMove(row, column, 7, 6, false);
						list.add(move);
					}

				} catch (Exception e) {
				}
			}

		}
		if (currentColor == Colors.BLACK) {

			if (controller.enableLeftBlackCastle == true) {
				try {// castle left black

					finalCondition = (Restrictions.checkSquareSafety(0, 4,
							Colors.BLACK, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 5,
									Colors.BLACK, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 6,
									Colors.BLACK, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 7,
									Colors.BLACK, chessBoard, controller)
							&& chessBoard[7][1] == controller.emptySpace && chessBoard[7][2] == controller.emptySpace);
					if (finalCondition == true) {
						move = new Movement();
						move.setMove(row, column, 7, 1, false);
						list.add(move);
					}

				} catch (Exception e) {
				}
			}
			if (controller.enableLeftBlackCastle == true) {
				try {// castle right black

					finalCondition = (Restrictions.checkSquareSafety(0, 0,
							Colors.BLACK, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 1,
									Colors.BLACK, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 2,
									Colors.BLACK, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 3,
									Colors.BLACK, chessBoard, controller)
							&& Restrictions.checkSquareSafety(0, 4,
									Colors.BLACK, chessBoard, controller)
							&& chessBoard[7][4] == controller.emptySpace
							&& chessBoard[7][5] == controller.emptySpace && chessBoard[7][6] == controller.emptySpace);
					if (finalCondition == true) {
						move = new Movement();
						move.setMove(row, column, 7, 5, false);
						list.add(move);
					}

				} catch (Exception e) {
				}
			}
		}
		return list;
	}
}
