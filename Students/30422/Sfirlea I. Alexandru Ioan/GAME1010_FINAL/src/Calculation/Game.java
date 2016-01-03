package Calculation;
import java.awt.Color;

import GUI.BoardPanel;
import Main.Constants;
import Pieces.Piece;
import Pieces.PieceHolder;

public class Game {

	private static int[][] backMatrix = new int[Constants.LENGHT][Constants.LENGHT];

	private static int[][] randomMatrix = new int[Constants.LENGHT ][Constants.LENGHT ];
	
	
	private int score = 0;
	private int highScore = 0;

	
	
	BackLogic logic = new BackLogic();
	
	private static Piece randPieceN;
	private static Piece randPieceO;
	public static boolean isNew = true;
	public static boolean isMoved;

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

	public int getTile(int x, int y) {
		return backMatrix[x][y];
	}

	public int getRandomTile(int x, int y) {
		return randomMatrix[y][x];
	}

	private void empty(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = 0;
			}
		}
	}

	
	public Color returnColor(int x, int y){
		int index = backMatrix[y][x]/10;
		return Constants.colorArray[index];
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

	public  boolean pieceAdded(){
		
		int X,Y;
		X = BoardPanel.mouse_X / Constants.TILE_SIZE;
		Y = BoardPanel.mouse_Y / Constants.TILE_SIZE;
		return logic.check(randPieceO.getForm(), randPieceO.getDimI(), randPieceO.getDimJ(), Y, X,
				backMatrix);
	}

	public void newGame(){
		empty(backMatrix);
		empty(randomMatrix);
		
		PieceHolder.storePiece(PieceHolder.hold);
		ColorPiker.storeColor(Constants.colorArray);
		randPieceN = PieceHolder.generateRandomPiece();
		randPieceO = PieceHolder.generateRandomPiece();
		score =0;
		
	}
	public void startGame() {
		
		if(Constants.isNewGame == 0 ){
			newGame();
		}
		Constants.isNewGame++;
		if (returnStatus() == true) {

			addRandomPiece(0, 0, randPieceN.getForm(), randPieceN.getDimJ(), randPieceN.getDimI());

			if ((BoardPanel.mouse_X < 435 - getRandPiece().getDimJ() * Constants.TILE_SIZE)
					&& (BoardPanel.mouse_Y < 435 - getRandPiece().getDimI() * Constants.TILE_SIZE)) {
				
				
				isMoved = pieceAdded();
				if ( isMoved== true) {
					
					addPiece(BoardPanel.back_X, BoardPanel.back_Y, randPieceO.getForm(), randPieceO.getDimJ(),
							randPieceO.getDimI());
					
					empty(randomMatrix);
					randPieceO = randPieceN;
					randPieceN = PieceHolder.generateRandomPiece();
					

				}
				

			}
			
			score = logic.deleteMatrix(backMatrix, score);
			if (score > highScore)
				highScore = score;	
		} 
		
	}
		
	

}