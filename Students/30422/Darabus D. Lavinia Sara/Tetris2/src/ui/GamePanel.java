package ui;

import java.awt.Color;
import java.awt.GridLayout;

import shapes.Shape;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.GameObserver;

public class GamePanel extends JPanel {

	private static final int BOARD_ROWS = 25;
	private static final int BOARD_COLS = 12;

	private JLabel[][] squares = new JLabel[BOARD_ROWS][BOARD_COLS];
	private Color[][] squareColors = new Color[BOARD_ROWS][BOARD_COLS];
	public Shape shape;
	public int currentRow, currentColumn;
	public boolean lineIsFilled;

	private int score;

	public GamePanel() {
		shape = new Shape();
		setLayout(new GridLayout(BOARD_ROWS, BOARD_COLS));
		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				this.setScore(0);
				squares[i][j] = new JLabel();
				squares[i][j].setBackground(Color.WHITE);
				squareColors[i][j] = Color.WHITE;
				squares[i][j].setOpaque(true);
				add(squares[i][j]);

			}
		}

		paintTetromino(shape.getPieceRow(), shape.getPieceCol(), shape.standardPiece);
	}

	public void paintCell(int row, int col, Color color) {
		squares[row][col].setBackground(color);
		if (squares[row][col].getBackground() != Color.WHITE) {
			squares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}

	public void paintTetromino(int deltaRow, int deltaColumn, boolean[][] pieceMatrix) {
		for (int i = 0; i < shape.getMatrixRows(); i++) {
			for (int j = 0; j < (shape.getMatrixCols()); j++) {
				if (pieceMatrix[i][j]) {
					paintCell(i + deltaRow, j + deltaColumn, shape.getRandomPieceColor());
				}
			}
		}
	}

	public void paintSquares(int deltaRow, int deltaColumn, boolean[][] pieceMatrix) {
		for (int i = 0; i < shape.getMatrixRows(); i++) {
			for (int j = 0; j < (shape.getMatrixCols()); j++) {
				if (pieceMatrix[i][j]) {

					squareColors[i + deltaRow][j + deltaColumn] = shape.getRandomPieceColor();
				}
			}
		}

	}

	public void updateBoard() {

		checkForFilledLines();
		paintBoard();

		checkIfMoveWasLegal(shape.standardPiece);
		paintTetromino(shape.getPieceRow(), shape.getPieceCol(), shape.standardPiece);
		checkIfFallingTerminated(shape.standardPiece);

	}

	public void checkIfMoveWasLegal(boolean[][] pieceMatrix) {
		for (int i = 0; i < shape.getMatrixRows(); i++) {
			for (int j = 0; j < (shape.getMatrixCols()); j++) {
				if (pieceMatrix[i][j]) {
					if (((j + shape.getPieceCol()) > (BOARD_COLS - 1)) || ((j + shape.getPieceCol()) < 0)
							|| ((i + shape.getPieceRow()) > (BOARD_ROWS - 1)) || // check
																					// for
																					// bounds
					((squares[i + shape.getPieceRow()][j + shape.getPieceCol()].getBackground()) != Color.WHITE)) { // check
																													// for
																													// other
																													// pieces
																													// already
																													// there
						shape.setPieceRow(shape.getPrevPieceRow());
						shape.setPieceCol(shape.getPrevPieceCol());
						shape.setMatrixRows(shape.getPrevMatrixRows());
						shape.setMatrixCols(shape.getPrevMatrixCols());

					}

				}
			}
		}
	}

	public void checkIfFallingTerminated(boolean[][] pieceMatrix) {
		for (int i = 0; i < shape.getMatrixRows(); i++) {

			for (int j = 0; j < (shape.getMatrixCols()); j++) {
				if (pieceMatrix[i][j]) {
					if ((i + shape.getPieceRow()) == (BOARD_ROWS - 1)
							|| (squareColors[i + shape.getPieceRow() + 1][j + shape.getPieceCol()] != Color.WHITE)) {
						paintSquares(shape.getPieceRow(), shape.getPieceCol(), shape.standardPiece);
						shape.setFallingIsTerminated(true);
						shape.decideIfGameEnds();
						shape = new Shape();
						break;

					} else {
						shape.setFallingIsTerminated(false);
					}
				}
			}
		}
	}

	public void paintBoard() {

		for (int i = 0; i < BOARD_ROWS; i++) {
			for (int j = 0; j < BOARD_COLS; j++) {
				squares[i][j].setBackground(squareColors[i][j]);
				if (squares[i][j].getBackground() != Color.WHITE) {
					squares[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				} else {
					squares[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
				}
			}
		}
	}

	public void checkForFilledLines() {
		int j = BOARD_COLS - 1;
		int i = BOARD_ROWS - 1;
		lineIsFilled = false;
		while (i >= 0) {

			while (j >= 0) {
				if (squareColors[i][j] != Color.WHITE) {
					lineIsFilled = true;
					j--;
				} else {
					lineIsFilled = false;
					j = BOARD_COLS - 1;
					break;
				}
			}
			if (lineIsFilled) {
				removeLine(i);
				this.setScore(this.getScore() + 1);
				lineIsFilled = false;

			}

			i--;

		}
	}

	public void removeLine(int row) {
		for (int i = row; i >= 0; i--) {
			for (int j = BOARD_COLS - 1; j >= 0; j--) {
				if (i == 0) {
					squareColors[i][j] = Color.WHITE;
				} else {
					squareColors[i][j] = squareColors[i - 1][j];
				}
			}
			paintBoard();
		}

	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}

}
