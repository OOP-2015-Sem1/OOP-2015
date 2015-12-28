package logic;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import game.Constants;
import logic.MoveValidator;

public class BackgammonGame {

	private MoveValidator moveValidator;
	public int gameState = Constants.GAME_STATE_WHITE;
	private List<Piece> pieces = new ArrayList<Piece>();

	public BackgammonGame() {

		this.moveValidator = new MoveValidator(this);

		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_1, Piece.COLUMN_5);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_2, Piece.COLUMN_5);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_3, Piece.COLUMN_5);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_1, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_2, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_3, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_4, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_5, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_19, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_20, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_21, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_22, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_23, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_22, Piece.COLUMN_14);
		createAndAddPiece(Constants.COLOR_WHITE, Piece.ROW_23, Piece.COLUMN_14);

		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_1, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_2, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_3, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_4, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_5, Piece.COLUMN_1);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_1, Piece.COLUMN_14);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_2, Piece.COLUMN_14);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_21, Piece.COLUMN_5);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_22, Piece.COLUMN_5);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_23, Piece.COLUMN_5);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_19, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_20, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_21, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_22, Piece.COLUMN_9);
		createAndAddPiece(Constants.COLOR_RED, Piece.ROW_23, Piece.COLUMN_9);

	}

	private void createAndAddPiece(int color, int row, int column) {
		Piece piece = new Piece(color, row, column);
		this.pieces.add(piece);
	}

	public boolean movePiece(int sourceRow, int sourceColumn, int targetRow, int targetColumn, int die) {

		if (die == 0)
			return false;
		if (!this.moveValidator.isMoveValid(sourceRow, sourceColumn, targetRow, targetColumn, die)) {
			System.out.println("move invalid");
			return false;
		}

		Piece piece = getNonCapturedPieceAtLocation(sourceRow, sourceColumn);

		// check if the move is capturing an opponent piece
		int opponentColor = (piece.getColor() == Constants.COLOR_RED ? Constants.COLOR_WHITE : Constants.COLOR_RED);
		if (isNonCapturedPieceAtLocation(opponentColor, targetRow, targetColumn)) {
			Piece opponentPiece = getNonCapturedPieceAtLocation(targetRow, targetColumn);

			if (piece.getColor() == Constants.COLOR_RED)
				opponentPiece.setRow(10);
			else
				opponentPiece.setRow(11);
			opponentPiece.setColumn(7);

			JOptionPane.showMessageDialog(null, "Put the piece on the bar after you roll the dice!", "Piece on bar",
					JOptionPane.PLAIN_MESSAGE);

		}
		piece.setRow(targetRow);
		piece.setColumn(targetColumn);
		return true;
	}

	// we can imagine our board game as 4 squares, every square has 6
	// columns.
	// Column 1 is in the lower-right corner, where there are two red pieces
	// at the beggining and we count columns clockwise.
	// so the parameter column is a number between 1-24

	public int noOfPiecesInAColumn(int startColumn, int color) {
		int noOfPieces = 0;

		for (Piece piece : this.pieces) {
			int column1 = moveValidator.convertBoardGameIntoColumns(piece.getRow(), piece.getColumn());
			if (startColumn == column1 && piece.getColor() == color)
				noOfPieces++;
		}
		return noOfPieces;
	}

	public int noOfPiecesOnBar(int color) {
		int noOfPieces = 0;

		for (Piece piece : this.pieces) {
			if (piece.getColumn() == 7 && piece.getColor() == color)
				noOfPieces++;
		}
		return noOfPieces;
	}

	public int noOfRemovedPieces(int color) {

		int noOfRemovedPieces = 0;

		for (Piece piece : this.pieces) {
			if ((piece.getColumn() == 15 || piece.getColumn() == 14) && piece.getColor() == color)
				noOfRemovedPieces++;
		}
		return noOfRemovedPieces;

	}

	// returns the first piece at the specified location that is not marked as
	// 'captured'.

	public Piece getNonCapturedPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false) {
				return piece;
			}
		}
		return null;
	}

	// Checks whether there is a piece at the specified location that is not
	// marked as 'captured' and has the specified color.
	// return true, if the location contains a not-captured piece of the
	// specified color

	private boolean isNonCapturedPieceAtLocation(int color, int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false
					&& piece.getColor() == color) {
				return true;
			}
		}
		return false;
	}

	public int getGameState() { // return current game state
		return this.gameState;
	}

	public List<Piece> getPieces() { // return the internal list of pieces
		return this.pieces;
	}

	public void changeGameState() { // switches the game state
		switch (this.gameState) {
		case Constants.GAME_STATE_RED:
			if (noOfRemovedPieces(Constants.GAME_STATE_RED) == 15) {
				this.gameState = Constants.GAME_STATE_END;
				JOptionPane.showMessageDialog(null, "Player with red pieces won!", null, JOptionPane.PLAIN_MESSAGE);

			}
			this.gameState = Constants.GAME_STATE_WHITE;
			break;
		case Constants.GAME_STATE_WHITE:
			if (noOfRemovedPieces(Constants.GAME_STATE_WHITE) == 15) {
				this.gameState = Constants.GAME_STATE_END;
				JOptionPane.showMessageDialog(null, "Player with white pieces won!", null, JOptionPane.PLAIN_MESSAGE);
			}
			this.gameState = Constants.GAME_STATE_RED;
			break;
		default:
			throw new IllegalStateException("unknown game state:" + this.gameState);
		}
	}

}
