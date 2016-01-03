package shapes;

import java.awt.Color;
import java.util.Random;

import core.GameObserver;
import core.TetrominoController;
import ui.GamePanel;
import ui.TetrisFrame;

public class Shape {

	private static final boolean[][] I_PIECE = { { true, true, true, true }, { false, false, false, false } };

	private static final boolean[][] J_PIECE = { { true, false, false, false }, { true, true, true, false } };

	private static final boolean[][] L_PIECE = { { false, false, true, false }, { true, true, true, false } };

	private static final boolean[][] O_PIECE = { { true, true, false, false }, { true, true, false, false } };

	private static final boolean[][] S_PIECE = { { false, true, true, false }, { true, true, false, false } };

	private static final boolean[][] T_PIECE = { { false, true, false, false }, { true, true, true, false } };

	private static final boolean[][] Z_PIECE = { { true, true, false, false }, { false, true, true, false } };

	public static boolean[][][] TETROMINOES = { I_PIECE, J_PIECE, L_PIECE, O_PIECE, S_PIECE, T_PIECE, Z_PIECE };

	private static Color[] TETROMINO_COLORS = { Color.red, Color.yellow, Color.magenta, Color.pink, Color.cyan,
			Color.green, Color.orange };

	public static int TETROMINO_MATRIX_ROWS = 2, TETROMINO_MATRIX_COLS = 4;

	private int pieceRow, pieceCol, prevPieceRow, prevPieceCol;
	private int prevMatrixRows, prevMatrixCols;

	private Color randomPieceColor;
	private int randomPieceNumber;
	private static int count;
	public boolean[][] standardPiece;
	private boolean[][] verticalPiece = new boolean[4][2];
	private boolean[][] horizontalPiece = new boolean[2][4];
	private int matrixRows, matrixCols;
	private int rotated;
	private boolean fallingIsTerminated;
	private GameObserver gameObserver;

	public Shape() {
		this.setMatrixRows(2);
		this.setMatrixCols(4);
		this.setRandomPieceNumber();
		this.generateNewFallingPiece();
		this.generatePieceColor();
		this.setPieceRow(0);
		this.setPieceCol(5);
		this.setRotated(0);
		this.setPrevMatrixRows(2);
		this.setPrevMatrixCols(4);
		this.setFallingIsTerminated(false);
	}

	public int getMatrixCols() {
		return matrixCols;
	}

	public void setMatrixCols(int matrixCols) {
		this.matrixCols = matrixCols;
	}

	public int getMatrixRows() {
		return matrixRows;
	}

	public void setMatrixRows(int matrixRows) {
		this.matrixRows = matrixRows;
	}

	public void setPieceRow(int row) {
		this.pieceRow = row;
	}

	public int getPieceRow() {
		return this.pieceRow;
	}

	public void setPieceCol(int y) {
		this.pieceCol = y;
	}

	public int getPieceCol() {
		return this.pieceCol;
	}

	public void setPrevPieceRow(int prevRow) {
		this.prevPieceRow = prevRow;
	}

	public int getPrevPieceRow() {
		return this.prevPieceRow;
	}

	public void setPrevPieceCol(int prevCol) {
		this.prevPieceCol = prevCol;
	}

	public int getPrevPieceCol() {
		return this.prevPieceCol;
	}

	public int generateRandomValue() {

		Random random = new Random();
		int randomValue = Math.abs(random.nextInt() % 6 + 1);

		return randomValue;

	}

	public void setRandomPieceNumber() {

		this.randomPieceNumber = generateRandomValue();

	}

	public int getRandomPieceNumber() {
		return this.randomPieceNumber;
	}

	public void generateNewFallingPiece() {
		this.standardPiece = new boolean[this.getMatrixRows()][this.getMatrixCols()];
		for (int i = 0; i < this.getMatrixRows(); i++) {
			for (int j = 0; j < (this.getMatrixCols()); j++) {
				this.standardPiece[i][j] = TETROMINOES[this.getRandomPieceNumber()][i][j];

			}
		}
	}

	public void generatePieceColor() {
		int randomColorNumber = generateRandomValue();
		this.randomPieceColor = TETROMINO_COLORS[randomColorNumber];
	}

	public Color getRandomPieceColor() {
		return this.randomPieceColor;
	}

	public void move(String direction) {
		// TODO Auto-generated method stub
		this.setPrevPieceRow(this.getPieceRow());
		this.setPrevPieceCol(this.getPieceCol());
		if (direction.equals("DOWN")) {
			this.decideIfGameEnds();
			this.setPieceRow(this.getPieceRow() + 1);

		} else if (direction.equals("RIGHT")) {
			this.setPieceCol(this.getPieceCol() + 1);
		} else if (direction.equals("LEFT")) {
			this.setPieceCol(this.getPieceCol() - 1);
		}

		else if (direction.equals("ROTATE"))
			this.rotatePiece("ROTATE");
	}

	public void rotatePiece(String direction) {
		if (direction.equals("ROTATE")) {

			this.setPrevPieceRow(this.getPieceRow());
			this.setPrevPieceCol(this.getPieceCol());
			int aux = this.getMatrixCols();
			this.setMatrixCols(this.getMatrixRows());
			this.setMatrixRows(aux);

			this.standardPiece = new boolean[this.getMatrixRows()][this.getMatrixCols()];
			if ((this.getMatrixRows() == 4) && (this.getMatrixCols() == 2)
					&& ((this.getRotated() == 0) || (this.getRotated() == 2))) { // vertical
																					// rotations
				count = 3;
				if (this.getRotated() == 2) {
					for (int i = 0; i < this.getMatrixRows(); i++) {

						for (int j = 0; j < this.getMatrixCols(); j++) {
							this.verticalPiece[i][j] = this.horizontalPiece[j][count];
							this.standardPiece[i][j] = this.verticalPiece[i][j];

						}
						count--;
					}

				} else {
					for (int i = 0; i < this.getMatrixRows(); i++) {

						for (int j = 0; j < this.getMatrixCols(); j++) {
							this.verticalPiece[i][j] = TETROMINOES[this.getRandomPieceNumber()][j][count];
							this.standardPiece[i][j] = this.verticalPiece[i][j];

						}
						count--;
					}
				}
				this.setRotated(this.getRotated() + 1);
				this.setPieceRow(this.getPieceRow() - 1);
				this.setPrevMatrixRows(2);
				this.setPrevMatrixCols(4);
			} else if ((this.getMatrixRows() == 2) && (this.getMatrixCols() == 4)
					&& ((this.getRotated() == 1) || (this.getRotated() == 3))) { // horizontal
																					// rotations
				count = 0;
				for (int i = 0; i < this.getMatrixRows(); i++) {
					count = 0;
					for (int j = 0; j < this.getMatrixCols(); j++) {
						this.horizontalPiece[i][j] = this.verticalPiece[count][Math.abs(i - 1)];
						this.standardPiece[i][j] = this.horizontalPiece[i][j];
						count++;
					}

				}
				if (this.getRotated() == 1) {
					this.setRotated(this.getRotated() + 1);
				} else if (this.getRotated() == 3) {
					this.setRotated(0);
				}

			}

			this.setPieceRow(this.getPieceRow() + 1);
			this.setPrevMatrixRows(4);
			this.setPrevMatrixCols(2);
		}

	}

	public int getRotated() {
		return rotated;
	}

	public void setRotated(int rotated) {
		this.rotated = rotated;
	}

	public int getPrevMatrixRows() {
		return prevMatrixRows;
	}

	public void setPrevMatrixRows(int prevMatrixRows) {
		this.prevMatrixRows = prevMatrixRows;
	}

	public int getPrevMatrixCols() {
		return prevMatrixCols;
	}

	public void setPrevMatrixCols(int prevMatrixCols) {
		this.prevMatrixCols = prevMatrixCols;
	}

	public boolean isFallingIsTerminated() {
		return fallingIsTerminated;
	}

	public void setFallingIsTerminated(boolean fallingIsTerminated) {
		this.fallingIsTerminated = fallingIsTerminated;
	}

	public void decideIfGameEnds() {
		if ((this.getPieceRow() == 0) && (this.isFallingIsTerminated())) {
			gameObserver.notifyLoss();
		}
	}

}
