package logic;

import game.Constants;
import logic.BackgammonGame;
import logic.MoveValidator;
import logic.Piece;

// sourceRow, sourceColumn, targetRow, targetColumn refers to the backgammon-board configuration, which has 23 rows and 16 columns
//(bar is considered as 2 columns and the places where pieces are bear off - 2 columns)
// startColumn, finishColumn refers to 24 columns, grouped 6 by 6 in quadrants, as in the figure below

/*      13 14 15 16 17 18 . 19 20 21 22 23 24
                          .
                          .
         . . . . . . . . . . . . . . . . . . .
                          .
                          .
        12 11 10  9  8  7 .  6  5  4  3  2  1
   */

public class MoveValidator {

	private BackgammonGame backgammonGame;
	private Piece sourcePiece;

	public MoveValidator(BackgammonGame backgammonGame) {
		this.backgammonGame = backgammonGame;
	}

	// return true if move is valid, false if move is invalid

	public boolean isMoveValid(int sourceRow, int sourceColumn, int targetRow, int targetColumn, int die) {

		sourcePiece = backgammonGame.getNonCapturedPieceAtLocation(sourceRow, sourceColumn);

		// source piece does not exist
		if (sourcePiece == null) {
			System.out.println("no source piece");
			return false;
		}

		// validate piece movement rules
		int startColumn = convertBoardGameIntoColumns(sourceRow, sourceColumn);
		int finishColumn = convertBoardGameIntoColumns(targetRow, targetColumn);

		if (isValidNormalMove(startColumn, finishColumn, die) || isValidPutPieceOnBar(startColumn, finishColumn, die)
				|| isValidTakePieceFromBar(die, 0, finishColumn) || isValidBearOff(die, startColumn, targetColumn)) {
			return true;
		}

		return false;
	}

	private boolean isValidNormalMove(int startColumn, int finishColumn, int die) {

		boolean result;
		if (backgammonGame.getGameState() ==  Constants.GAME_STATE_WHITE) {
			if (finishColumn == (startColumn - die)
					&& backgammonGame.noOfPiecesInAColumn(finishColumn,  Constants.GAME_STATE_RED) == 0
					&& backgammonGame.noOfPiecesOnBar( Constants.GAME_STATE_WHITE) == 0)
				result = true;
			else
				result = false;
		} else {
			if (finishColumn == (startColumn + die)
					&& backgammonGame.noOfPiecesInAColumn(finishColumn,  Constants.GAME_STATE_WHITE) == 0
					&& backgammonGame.noOfPiecesOnBar( Constants.GAME_STATE_RED) == 0)
				result = true;
			else
				result = false;
		}
		return result;
	}

	private boolean isValidPutPieceOnBar(int startColumn, int finishColumn, int die) {
		boolean result = false;
		if (backgammonGame.getGameState() ==  Constants.GAME_STATE_WHITE) {
			if (finishColumn == (startColumn - die)
					&& backgammonGame.noOfPiecesInAColumn(finishColumn,  Constants.GAME_STATE_RED) == 1)
				result = true;
			else
				result = false;
		}
		if (backgammonGame.getGameState() ==  Constants.GAME_STATE_RED) {
			if (finishColumn == (startColumn + die)
					&& backgammonGame.noOfPiecesInAColumn(finishColumn, Constants.GAME_STATE_WHITE) == 1)
				result = true;
			else
				result = false;
		}
		return result;
	}

	private boolean isValidTakePieceFromBar(int die, int startColumn, int finishColumn) {
		boolean result = false;
		if (backgammonGame.getGameState() ==  Constants.GAME_STATE_WHITE) {
			if (finishColumn == (25 - die)
					&& backgammonGame.noOfPiecesInAColumn(finishColumn,  Constants.GAME_STATE_RED) <= 1)
				result = true;
			else
				result = false;
		}
		if (backgammonGame.getGameState() ==  Constants.GAME_STATE_RED)
			if (finishColumn == die
					&& backgammonGame.noOfPiecesInAColumn(finishColumn,  Constants.GAME_STATE_RED) <= 1)
				result = true;
			else
				result = false;
		return result;

	}

	// remove a piece from the board according to a roll of the dice after all
	// the pieces have been brought into home board.
	// we need to pay attention to the fact that if our pieces are at home, if
	// we roll dice and we get 6, and column 6 is empty, the move is still
	// possible, with a smaller column
	private boolean isValidBearOff(int die, int startColumn, int targetColumn) {
		boolean result = false;

		if (backgammonGame.getGameState() ==  Constants.GAME_STATE_WHITE) {
			int noOfPiecesHome = 0;
			for (int i = 1; i <= 6; i++)
				noOfPiecesHome += backgammonGame.noOfPiecesInAColumn(i,  Constants.GAME_STATE_WHITE);
			int noOfRemovedPieces = 0;
			noOfRemovedPieces += backgammonGame.noOfRemovedPieces( Constants.GAME_STATE_WHITE);
			if (noOfRemovedPieces + noOfPiecesHome == 15 && targetColumn == 15) {
				if (startColumn == die)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(die,  Constants.GAME_STATE_WHITE) == 0
						&& startColumn == die - 1)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(die,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 1,  Constants.GAME_STATE_WHITE) == 0
						&& startColumn == die - 2)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(die,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 1,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 2,  Constants.GAME_STATE_WHITE) == 0
						&& startColumn == die - 3)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(die,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 1,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 2,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 3,  Constants.GAME_STATE_WHITE) == 0
						&& startColumn == die - 4)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(die,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 1,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 2,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 3,  Constants.GAME_STATE_WHITE) == 0
						&& backgammonGame.noOfPiecesInAColumn(die - 4,  Constants.GAME_STATE_WHITE) == 0
						&& startColumn == die - 5)
					result = true;
			} else
				result = false;
		}
		if (backgammonGame.getGameState() == Constants.GAME_STATE_RED) {
			int noOfPiecesHome = 0;
			for (int i = 19; i <= 24; i++)
				noOfPiecesHome += backgammonGame.noOfPiecesInAColumn(i, Constants.GAME_STATE_RED);
			int noOfRemovedPieces = 0;
			noOfRemovedPieces += backgammonGame.noOfRemovedPieces(Constants.GAME_STATE_RED);
			if (noOfRemovedPieces + noOfPiecesHome == 15 && targetColumn == 15) {
				if (startColumn == (25 - die))
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(25 - die, Constants.GAME_STATE_RED) == 0
						&& startColumn == 25 - die + 1)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(25 - die, Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 1, Constants.GAME_STATE_RED) == 0
						&& startColumn == 25 - die + 2)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(25 - die,Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 1, Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 2, Constants.GAME_STATE_RED) == 0
						&& startColumn == 25 - die + 3)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(25 - die,Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 1, Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 2, Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 3, Constants.GAME_STATE_RED) == 0
						&& startColumn == 25 - die + 4)
					result = true;
				else if (backgammonGame.noOfPiecesInAColumn(25 - die, Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 1, Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 2,  Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 3,  Constants.GAME_STATE_RED) == 0
						&& backgammonGame.noOfPiecesInAColumn(25 - die + 4,  Constants.GAME_STATE_RED) == 0
						&& startColumn == 25 - die + 5)
					result = true;

			}

			else
				result = false;
		}
		return result;

	}

	public int convertBoardGameIntoColumns(int sourceRow, int sourceColumn) {

		// we can imagine our board game as 4 squares, every square has 6
		// columns.
		// Column 1 is in the lower-right corner, where there are two red pieces
		// at the beggining and we count columns clockwise.
		int column = 0;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 13)
			column = 1;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 12)
			column = 2;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 11)
			column = 3;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 10)
			column = 4;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 9)
			column = 5;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 8)
			column = 6;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 5)
			column = 7;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 4)
			column = 8;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 3)
			column = 9;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 2)
			column = 10;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 1)
			column = 11;
		if (sourceRow >= 1 && sourceRow <= 10 && sourceColumn == 0)
			column = 12;

		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 0)
			column = 13;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 1)
			column = 14;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 2)
			column = 15;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 3)
			column = 16;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 4)
			column = 17;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 5)
			column = 18;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 8)
			column = 19;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 9)
			column = 20;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 10)
			column = 21;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 11)
			column = 22;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 12)
			column = 23;
		if (sourceRow >= 14 && sourceRow <= 23 && sourceColumn == 13)
			column = 24;
		return column;

	}
}
