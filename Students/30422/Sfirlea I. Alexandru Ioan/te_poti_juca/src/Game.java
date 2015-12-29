import java.awt.Color;

public class Game {

	private static int[][] backMatrix = new int[Constants.LENGHT][Constants.LENGHT];

	private static int[][] randomMatrix = new int[Constants.LENGHT / 2][Constants.LENGHT / 2];

	private int score = 0;
	private int highScore = 0;

	private int ok = 0;
	BackLogic logic = new BackLogic();
	private static Piece randPieceN;
	private static Piece randPieceO;
	public static boolean isNew = true;

	public Piece getRandPiece() {
		return randPieceO;
	}

	public void addPiece(int x, int y, int[][] matrix, int dimX, int dimY) {
		/*
		 * Loop through every tile within the piece and add it to the board only
		 * if the boolean that represents that tile is set to true.
		 */
		for (int col = 0; col < dimY; col++) {
			for (int row = 0; row < dimX; row++) {
				if (matrix[col][row] != 0) {
					setBackMatrix(col + x, row + y, matrix[col][row]);
				}
			}
		}
	}

	public void addRandomPiece(int x, int y, int[][] matrix, int dimX, int dimY) {
		for (int col = 0; col < dimY; col++) {
			for (int row = 0; row < dimX; row++) {
				if (matrix[col][row] != 0) {
					setRandomMatrix(col + x, row + y, matrix[col][row]);
				}
			}
		}
	}

	private void setRandomMatrix(int x, int y, int elem) {
		randomMatrix[x][y] = elem;
	}

	private void setBackMatrix(int x, int y, int elem) {
		backMatrix[x][y] = elem;
	}

	/**
	 * Gets a tile by it's column and row from backmatrix.
	 */
	public int getTile(int x, int y) {
		return backMatrix[y][x];
	}

	public int getRandomTile(int x, int y) {
		return randomMatrix[y][x];
	}

	private void empty(int[][] bmatrix) {
		for (int i = 0; i < bmatrix.length; i++) {
			for (int j = 0; j < bmatrix.length; j++) {
				bmatrix[i][j] = 0;
			}
		}
	}

	public static Color getOldColor() {
		return randPieceO.getColor();
	}

	public static Color getNewColor() {
		return randPieceN.getColor();
	}

	public int getScore() {
		return score;
	}

	public int getHighScore() {
		return highScore;
	}

	public boolean returnStatus() {
		return logic.gameOver(randPieceO, backMatrix);
	}

	public boolean newGame() {

		empty(backMatrix);
		empty(randomMatrix);
		PieceHolder.storePiece(PieceHolder.hold);
		randPieceN = PieceHolder.generateRandomPiece();
		randPieceO = PieceHolder.generateRandomPiece();
		return false;
	}

	public void startGame() {

		if (ok == 0) {
			empty(backMatrix);
			empty(randomMatrix);
			PieceHolder.storePiece(PieceHolder.hold);
			randPieceN = PieceHolder.generateRandomPiece();
			randPieceO = PieceHolder.generateRandomPiece();

		}
		ok = 1;
		if (returnStatus() == true) {

			addRandomPiece(0, 0, randPieceN.getForm(), randPieceN.getDimJ(), randPieceN.getDimI());

			if ((BoardPanel.mouse_X < 435 - getRandPiece().getDimJ() * Constants.TILE_SIZE)
					&& (BoardPanel.mouse_Y < 435 - getRandPiece().getDimI() * Constants.TILE_SIZE)) {
				int x, y;
				x = BoardPanel.mouse_X / Constants.TILE_SIZE;
				y = BoardPanel.mouse_Y / Constants.TILE_SIZE;

				if (logic.check(randPieceO.getForm(), randPieceO.getDimI(), randPieceO.getDimJ(), y, x,
						backMatrix) == true) {
					
					addPiece(BoardPanel.back_X, BoardPanel.back_Y, randPieceO.getForm(), randPieceO.getDimJ(),
							randPieceO.getDimI());
					BoardPanel.mouse_X = 999;
					empty(randomMatrix);
					randPieceO = randPieceN;
					randPieceN = PieceHolder.generateRandomPiece();

				}

			}
			score = logic.deleteMatrix(backMatrix, score);
			if (score > highScore)
				highScore = score;
			randPieceO.getColor();
		} 
		
	}

}
