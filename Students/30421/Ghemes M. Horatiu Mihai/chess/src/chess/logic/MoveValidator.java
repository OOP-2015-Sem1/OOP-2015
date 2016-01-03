package chess.logic;

public class MoveValidator {

	private ChessGame chessGame;
	private Piece sourcePiece;
	private Piece targetPiece;
	private Move lastMove;
	private int kappa;

	public MoveValidator(ChessGame chessGame) {
		this.chessGame = chessGame;
	}

	public boolean isMoveValid(Move move) {
		int sourceRow = move.sourceRow;
		int sourceColumn = move.sourceColumn;
		int targetRow = move.targetRow;
		int targetColumn = move.targetColumn;

		sourcePiece = chessGame.getNonCapturedPieceAtLocation(sourceRow, sourceColumn);
		targetPiece = this.chessGame.getNonCapturedPieceAtLocation(targetRow, targetColumn);

		// I sure hope we're clicking on the board
		if (targetRow < Piece.ROW_1 || targetRow > Piece.ROW_8 || targetColumn < Piece.COLUMN_A
				|| targetColumn > Piece.COLUMN_H) {
			System.out.println("Try clicking somewhere on the board");
			return false;
		}

		// Still not ok
		if (sourcePiece == null) {
			System.out.println("Good, now try clicking on a piece");
			return false;
		}

		// We don't cut in line m8
		if (sourcePiece.getColor() == Piece.COLOR_WHITE
				&& this.chessGame.getGameState() == ChessGame.GAME_STATE_WHITE) {

		} else if (sourcePiece.getColor() == Piece.COLOR_BLACK
				&& this.chessGame.getGameState() == ChessGame.GAME_STATE_BLACK) {

		} else {
			System.out.println("Please wait for your opponent's move");
			return false;
		}

		// Wait, you're telling me that the King is far weaker than the Queen?
		boolean validPieceMove = false;
		switch (sourcePiece.getType()) {
		case Piece.TYPE_BISHOP:
			validPieceMove = isValidBishopMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_KING:
			validPieceMove = isValidKingMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_KNIGHT:
			validPieceMove = isValidKnightMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_PAWN:
			validPieceMove = isValidPawnMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_QUEEN:
			validPieceMove = isValidQueenMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		case Piece.TYPE_ROOK:
			validPieceMove = isValidRookMove(sourceRow, sourceColumn, targetRow, targetColumn);
			break;
		default:
			break;
		}
		if (!validPieceMove) {
			return false;
		} else {
			// ok
		}

		return true;
	}

	private boolean isTargetLocationCaptureable() {
		if (targetPiece == null) {
			return false;
		} else if (targetPiece.getColor() != sourcePiece.getColor()) { // Can
																		// only
																		// capture
																		// opponent's
																		// pieces
			return true;
		} else {
			return false; // special condis
		}
	}

	private boolean isTargetLocationFree() {
		return targetPiece == null;
	}

	private boolean isValidBishopMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// So, diagonals only

		if (isTargetLocationFree() || isTargetLocationCaptureable()) {

		} else {
			System.out.println("Invalid move");
			return false;
		}

		boolean isValid = false;
		// checking for diagonallity
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;

		if (diffRow == diffColumn && diffColumn > 0) {
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, +1, +1);

		} else if (diffRow == -diffColumn && diffColumn > 0) {
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, -1, +1);

		} else if (diffRow == diffColumn && diffColumn < 0) {
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, -1, -1);

		} else if (diffRow == -diffColumn && diffColumn < 0) {
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, +1, -1);

		} else {
			// not moving diagonally
			System.out.println(diffRow);
			System.out.println(diffColumn);
			System.out.println("Invalid move");
			isValid = false;
		}
		return isValid;
	}

	private boolean isValidQueenMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// It's like a Rook and a Bishop's offspring, but on steroids
		boolean result = isValidBishopMove(sourceRow, sourceColumn, targetRow, targetColumn);
		result |= isValidRookMove(sourceRow, sourceColumn, targetRow, targetColumn);
		return result;
	}

	private boolean isValidPawnMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

		boolean isValid = false;
		// So you're telling me that a Pawn can be promoted to a Queen?
		if (isTargetLocationFree()) {
			if (sourceColumn == targetColumn) {
				// only moves forward
				if (sourcePiece.getColor() == Piece.COLOR_WHITE) {
					if (sourceRow + 1 == targetRow || (sourceRow + 2 == targetRow && sourceRow - 1 == 0)) {
						// move 2 spots if it's in starting position, move 1 in
						// other case
						isValid = true;
					} else {
						System.out.println("Invalid move");
						isValid = false;
					}
				} else {
					// black
					if (sourceRow - 1 == targetRow || (sourceRow - 2 == targetRow && sourceRow + 1 == 7)) {
						// same as above
						isValid = true;
					} else {
						System.out.println("Invalid move");
						isValid = false;
					}
				}
				// en passant validations
			}
			/*
			 * else if(targetColumn==sourceColumn+1 && targetRow==5 &&
			 * sourceRow==4 && sourcePiece.getColor() == Piece.COLOR_WHITE &&
			 * this.lastMove.sourceRow==6 && this.lastMove.targetRow==4) {
			 * 
			 * if(chessGame.getNonCapturedPieceAtLocation(sourceRow,
			 * sourceColumn+1)!= null &&
			 * chessGame.getNonCapturedPieceAtLocation(sourceRow,
			 * sourceColumn+1).getType()==6) isValid = true; else isValid=false;
			 * } else if(targetColumn==sourceColumn-1 && targetRow==5 &&
			 * sourceRow==4 && sourcePiece.getColor() == Piece.COLOR_WHITE) {
			 * 
			 * if(chessGame.getNonCapturedPieceAtLocation(sourceRow,
			 * sourceColumn-1)!= null &&
			 * chessGame.getNonCapturedPieceAtLocation(sourceRow,
			 * sourceColumn-1).getType()==6) isValid = true; else isValid=false;
			 * } else if((targetColumn==sourceColumn+1 ||
			 * targetColumn==sourceColumn-1) && targetRow==2 && sourceRow==3 &&
			 * sourcePiece.getColor() == Piece.COLOR_BLACK) { isValid = true; }
			 */
			else {
				System.out.println("Invalid move");
				isValid = false;
			}

		}
		// It can capture enemy pieces situated 1 spot across them, diagonally
		else if (isTargetLocationCaptureable()) {

			if (sourceColumn + 1 == targetColumn || sourceColumn - 1 == targetColumn) {
				// one column to the right or left
				if (sourcePiece.getColor() == Piece.COLOR_WHITE) {
					// white
					if (sourceRow + 1 == targetRow) {
						// move one up
						isValid = true;
					} else {
						System.out.println("Invalid move");
						isValid = false;
					}
				} else {
					// black
					if (sourceRow - 1 == targetRow) {
						// move one down
						isValid = true;
					} else {
						System.out.println("Invalid move");
						isValid = false;
					}
				}
			} else {
				// note one column to the left or right
				System.out.println("Invalid move");
				isValid = false;
			}
		}

		return isValid;
	}

	private boolean isValidKnightMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		// L-type moves

		if (isTargetLocationFree() || isTargetLocationCaptureable()) {

		} else {
			System.out.println("Invalid move");
			return false;
		}

		if (sourceRow + 2 == targetRow && sourceColumn + 1 == targetColumn) {
			// move up up right
			return true;
		} else if (sourceRow + 1 == targetRow && sourceColumn + 2 == targetColumn) {
			// move up right right
			return true;
		} else if (sourceRow - 1 == targetRow && sourceColumn + 2 == targetColumn) {
			// move down right right
			return true;
		} else if (sourceRow - 2 == targetRow && sourceColumn + 1 == targetColumn) {
			// move down down right
			return true;
		} else if (sourceRow - 2 == targetRow && sourceColumn - 1 == targetColumn) {
			// move down down left
			return true;
		} else if (sourceRow - 1 == targetRow && sourceColumn - 2 == targetColumn) {
			// move down left left
			return true;
		} else if (sourceRow + 1 == targetRow && sourceColumn - 2 == targetColumn) {
			// move up left left
			return true;
		} else if (sourceRow + 2 == targetRow && sourceColumn - 1 == targetColumn) {
			// move up up left
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidKingMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {

		if (isTargetLocationFree() || isTargetLocationCaptureable()) {

		} else {
			System.out.println("Invalid move");
			return false;
		}

		boolean isValid = true;
		if (sourceRow + 1 == targetRow && sourceColumn == targetColumn) {
			// up
			isValid = true;
		} else if (sourceRow + 1 == targetRow && sourceColumn + 1 == targetColumn) {
			// up right
			isValid = true;
		} else if (sourceRow == targetRow && sourceColumn + 1 == targetColumn) {
			// right
			isValid = true;
		} else if (sourceRow - 1 == targetRow && sourceColumn + 1 == targetColumn) {
			// down right
			isValid = true;
		} else if (sourceRow - 1 == targetRow && sourceColumn == targetColumn) {
			// down
			isValid = true;
		} else if (sourceRow - 1 == targetRow && sourceColumn - 1 == targetColumn) {
			// down left
			isValid = true;
		} else if (sourceRow == targetRow && sourceColumn - 1 == targetColumn) {
			// left
			isValid = true;
		} else if (sourceRow + 1 == targetRow && sourceColumn - 1 == targetColumn) {
			// up left
			isValid = true;
			// white king right side castle - not finished

		} /*
			 * else if(targetRow==0 && targetColumn==6 && sourcePiece.getColor()
			 * == Piece.COLOR_WHITE) { if(sourcePiece.wkFlag &&
			 * sourcePiece.wrrFlag &&
			 * !arePiecesBetweenSourceAndTarget(0,4,0,6,0,1)) isValid=true; else
			 * isValid=false;
			 * 
			 * }
			 */
		else {
			System.out.println("Invalid move");
			isValid = false;
		}

		// castling
		// ..

		return isValid;
	}

	private boolean isValidRookMove(int sourceRow, int sourceColumn, int targetRow, int targetColumn) {
		if (isTargetLocationFree() || isTargetLocationCaptureable()) {
		} else {
			System.out.println("Invalid move");
			return false;
		}

		boolean isValid = false;

		// straight path
		int diffRow = targetRow - sourceRow;
		int diffColumn = targetColumn - sourceColumn;

		if (diffRow == 0 && diffColumn > 0) {
			// right
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, 0, +1);

		} else if (diffRow == 0 && diffColumn < 0) {
			// left
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, 0, -1);

		} else if (diffRow > 0 && diffColumn == 0) {
			// up
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, +1, 0);

		} else if (diffRow < 0 && diffColumn == 0) {
			// down
			isValid = !arePiecesBetweenSourceAndTarget(sourceRow, sourceColumn, targetRow, targetColumn, -1, 0);

		} else {
			System.out.println("Invalid move");
			isValid = false;
		}

		return isValid;
	}

	private boolean arePiecesBetweenSourceAndTarget(int sourceRow, int sourceColumn, int targetRow, int targetColumn,
			int rowIncrementPerStep, int columnIncrementPerStep) {

		int currentRow = sourceRow + rowIncrementPerStep;
		int currentColumn = sourceColumn + columnIncrementPerStep;
		while (true) {
			if (currentRow == targetRow && currentColumn == targetColumn) {
				break;
			}
			if (currentRow < Piece.ROW_1 || currentRow > Piece.ROW_8 || currentColumn < Piece.COLUMN_A
					|| currentColumn > Piece.COLUMN_H) {
				break;
			}

			if (this.chessGame.isNonCapturedPieceAtLocation(currentRow, currentColumn)) {
				System.out.println("No straight path");
				return true;
			}

			currentRow += rowIncrementPerStep;
			currentColumn += columnIncrementPerStep;
		}
		return false;
	}

	public static void main(String[] args) {
		ChessGame ch = new ChessGame();
		MoveValidator mo = new MoveValidator(ch);
		Move move = null;
		boolean isValid;

		int sourceRow;
		int sourceColumn;
		int targetRow;
		int targetColumn;
		int testCounter = 1;

		sourceRow = Piece.ROW_2;
		sourceColumn = Piece.COLUMN_D;
		targetRow = Piece.ROW_3;
		targetColumn = Piece.COLUMN_D;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		ch.movePiece(move);
		System.out.println(testCounter + ". test result: " + (true == isValid));
		testCounter++;

		sourceRow = Piece.ROW_2;
		sourceColumn = Piece.COLUMN_B;
		targetRow = Piece.ROW_3;
		targetColumn = Piece.COLUMN_B;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		System.out.println(testCounter + ". test result: " + (false == isValid));
		testCounter++;

		sourceRow = Piece.ROW_7;
		sourceColumn = Piece.COLUMN_E;
		targetRow = Piece.ROW_6;
		targetColumn = Piece.COLUMN_E;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		ch.movePiece(move);
		System.out.println(testCounter + ". test result: " + (true == isValid));
		testCounter++;

		sourceRow = Piece.ROW_1;
		sourceColumn = Piece.COLUMN_F;
		targetRow = Piece.ROW_4;
		targetColumn = Piece.COLUMN_C;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		System.out.println(testCounter + ". test result: " + (false == isValid));
		testCounter++;

		sourceRow = Piece.ROW_1;
		sourceColumn = Piece.COLUMN_C;
		targetRow = Piece.ROW_4;
		targetColumn = Piece.COLUMN_F;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		ch.movePiece(move);
		System.out.println(testCounter + ". test result: " + (true == isValid));
		testCounter++;

		sourceRow = Piece.ROW_8;
		sourceColumn = Piece.COLUMN_B;
		targetRow = Piece.ROW_6;
		targetColumn = Piece.COLUMN_C;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		ch.movePiece(move);
		System.out.println(testCounter + ". test result: " + (true == isValid));
		testCounter++;

		// invalid knight move
		sourceRow = Piece.ROW_1;
		sourceColumn = Piece.COLUMN_G;
		targetRow = Piece.ROW_3;
		targetColumn = Piece.COLUMN_G;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		System.out.println(testCounter + ". test result: " + (false == isValid));
		testCounter++;

		// invalid knight move
		sourceRow = Piece.ROW_1;
		sourceColumn = Piece.COLUMN_G;
		targetRow = Piece.ROW_2;
		targetColumn = Piece.COLUMN_E;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		System.out.println(testCounter + ". test result: " + (false == isValid));
		testCounter++;

		// ok
		sourceRow = Piece.ROW_1;
		sourceColumn = Piece.COLUMN_G;
		targetRow = Piece.ROW_3;
		targetColumn = Piece.COLUMN_H;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		ch.movePiece(move);
		System.out.println(testCounter + ". test result: " + (true == isValid));
		testCounter++;

		// pieces in between
		sourceRow = Piece.ROW_8;
		sourceColumn = Piece.COLUMN_A;
		targetRow = Piece.ROW_5;
		targetColumn = Piece.COLUMN_A;
		move = new Move(sourceRow, sourceColumn, targetRow, targetColumn);
		isValid = mo.isMoveValid(move);
		ch.movePiece(move);
		System.out.println(testCounter + ". test result: " + (false == isValid));
		testCounter++;

	}

}
