package chess.logic;

import java.util.ArrayList;
import java.util.List;

public class ChessGame {

	private int gameState = GAME_STATE_WHITE;
	public static final int GAME_STATE_WHITE = 0;
	public static final int GAME_STATE_BLACK = 1;
	public static final int GAME_STATE_END = 2;

	private List<Piece> pieces = new ArrayList<Piece>();

	private MoveValidator moveValidator;

	public ChessGame() {

		this.moveValidator = new MoveValidator(this);

		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_A);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_B);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, Piece.COLUMN_C);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_QUEEN, Piece.ROW_1, Piece.COLUMN_D);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KING, Piece.ROW_1, Piece.COLUMN_E);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_BISHOP, Piece.ROW_1, Piece.COLUMN_F);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_KNIGHT, Piece.ROW_1, Piece.COLUMN_G);
		createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_ROOK, Piece.ROW_1, Piece.COLUMN_H);

		// pawns
		int currentColumn = Piece.COLUMN_A;
		for (int i = 0; i < 8; i++) {
			createAndAddPiece(Piece.COLOR_WHITE, Piece.TYPE_PAWN, Piece.ROW_2, currentColumn);
			currentColumn++;
		}

		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_A);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_B);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_C);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_QUEEN, Piece.ROW_8, Piece.COLUMN_D);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KING, Piece.ROW_8, Piece.COLUMN_E);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_BISHOP, Piece.ROW_8, Piece.COLUMN_F);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_KNIGHT, Piece.ROW_8, Piece.COLUMN_G);
		createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_ROOK, Piece.ROW_8, Piece.COLUMN_H);

		// pawns
		currentColumn = Piece.COLUMN_A;
		for (int i = 0; i < 8; i++) {
			createAndAddPiece(Piece.COLOR_BLACK, Piece.TYPE_PAWN, Piece.ROW_7, currentColumn);
			currentColumn++;
		}
	}

	private void createAndAddPiece(int color, int type, int row, int column) {
		Piece piece = new Piece(color, type, row, column);
		this.pieces.add(piece);
	}

	public boolean movePiece(Move move) {

		if (!this.moveValidator.isMoveValid(move)) {
			System.out.println("Invalid move");
			return false;
		}

		Piece piece = getNonCapturedPieceAtLocation(move.sourceRow, move.sourceColumn);

		// check if we're capturing something
		int opponentColor = (piece.getColor() == Piece.COLOR_BLACK ? Piece.COLOR_WHITE : Piece.COLOR_BLACK);
		if (isNonCapturedPieceAtLocation(opponentColor, move.targetRow, move.targetColumn)) {
			Piece opponentPiece = getNonCapturedPieceAtLocation(move.targetRow, move.targetColumn);
			opponentPiece.isCaptured(true);
		}

		piece.setRow(move.targetRow);
		piece.setColumn(move.targetColumn);

		if (isGameEndConditionReached()) {
			this.gameState = GAME_STATE_END;
		} else {
			this.changeGameState();
		}
		return true;
	}

	private boolean isGameEndConditionReached() {
		for (Piece piece : this.pieces) {
			if (piece.getType() == Piece.TYPE_KING && piece.isCaptured()) {
				return true;
			} else {
				// continue iterating
			}
		}

		return false;
	}

	public Piece getNonCapturedPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false) {
				return piece;
			}
		}
		return null;
	}

	private boolean isNonCapturedPieceAtLocation(int color, int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false
					&& piece.getColor() == color) {
				return true;
			}
		}
		return false;
	}

	public boolean isNonCapturedPieceAtLocation(int row, int column) {
		for (Piece piece : this.pieces) {
			if (piece.getRow() == row && piece.getColumn() == column && piece.isCaptured() == false) {
				return true;
			}
		}
		return false;
	}

	public int getGameState() {
		return this.gameState;
	}

	public List<Piece> getPieces() {
		return this.pieces;
	}

	public void changeGameState() {

		if (this.isGameEndConditionReached()) {

			if (this.gameState == ChessGame.GAME_STATE_BLACK) {
				System.out.println("Game over! Black won!");
			} else {
				System.out.println("Game over! White won!");
			}

			this.gameState = ChessGame.GAME_STATE_END;
			return;
		}

		switch (this.gameState) {
		case GAME_STATE_BLACK:
			this.gameState = GAME_STATE_WHITE;
			break;
		case GAME_STATE_WHITE:
			this.gameState = GAME_STATE_BLACK;
			break;
		case GAME_STATE_END:
			break;
		default:
			throw new IllegalStateException("unknown game state:" + this.gameState);
		}
	}

	public MoveValidator getMoveValidator() {
		return this.moveValidator;
	}

}
