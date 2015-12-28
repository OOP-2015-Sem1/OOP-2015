package Calculation;

import java.awt.Color;
import java.io.File;
import java.nio.channels.ReadPendingException;

import GUI.BoardPanel;
import GUI.Frame1010;
import GUI.Sounds;
import Main.Constants;
import Pieces.ColorPiker;
import Pieces.Piece;
import Pieces.PieceHolder;

public class Game {

	private static int[][] backMatrix = new int[Constants.LENGHT][Constants.LENGHT];
	private static int[][] randomMatrix = new int[Constants.LENGHT][Constants.LENGHT];
	public static String[] playerScore = new String[Constants.NROFPLAYERS];
	public static String[] playerName = new String[Constants.NROFPLAYERS];

	private int score = 0;
	private int highScore;
	BackLogic logic = new BackLogic();
	private static Sounds sound = new Sounds();
	private static Piece randPieceN;
	private static Piece randPieceO;
	public static boolean isNew = true;
	public static boolean isMoved;
	private static String[] date = new String[Constants.NROFPLAYERS];
	private static int printJustOnce = 0;

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

	public Color returnColor(int x, int y) {
		int index = backMatrix[y][x] / 10;
		return Constants.colorArray[index];
	}
	
	public static Color getOldColor(){
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

	public boolean pieceAdded() {

		int X, Y;
		X = BoardPanel.mouse_X / Constants.TILE_SIZE;
		Y = BoardPanel.mouse_Y / Constants.TILE_SIZE;
		return logic.check(randPieceO.getForm(), randPieceO.getDimI(), randPieceO.getDimJ(), Y, X, backMatrix);
	}

	public void newRecord(int score) {

		int i = 0;
		int index;
		while (score < Integer.valueOf(playerScore[i])) {
			i++;
		}

		for (index = Constants.NROFPLAYERS-1; index > i+1; index--) {
			playerScore[index] = playerScore[index-1];
			playerName[index] = playerName[index-1];
		}

		playerScore[i] = String.valueOf(score);
		playerName[i] = Frame1010.playerName;

		FileIO.writeIO(Constants.scoreFile, playerScore);
		FileIO.writeIO(Constants.playerFile, playerName);
	}

	public void newGame() {
		empty(backMatrix);
		empty(randomMatrix);
		FileIO.readPlayers(playerName, playerScore);

		PieceHolder.storePiece(PieceHolder.hold);
		ColorPiker.storeColor(Constants.colorArray);
		randPieceN = PieceHolder.generateRandomPiece();
		randPieceO = PieceHolder.generateRandomPiece();

		highScore = FileIO.convertDate(playerScore, 0);
		score = 0;
		printJustOnce = 0;
		Constants.highScoreOn = false;
		
		
		
	}

	public void startGame() {

		if (Constants.isNewGame == 0) {
			newGame();
			Constants.isNewGame=1;
		}
		
		if (returnStatus() == true) {
			printJustOnce=0;
			
			addRandomPiece(0, 0, randPieceN.getForm(), randPieceN.getDimJ(), randPieceN.getDimI());

			if ((BoardPanel.mouse_X < 435 - getRandPiece().getDimJ() * Constants.TILE_SIZE)
					&& (BoardPanel.mouse_Y < 435 - getRandPiece().getDimI() * Constants.TILE_SIZE)) {

				isMoved = pieceAdded();
				if (isMoved == true) {
					
					addPiece(BoardPanel.back_X, BoardPanel.back_Y, randPieceO.getForm(), randPieceO.getDimJ(),
							randPieceO.getDimI());
					BoardPanel.mouse_X=999;
					empty(randomMatrix);
					randPieceO = randPieceN;
					randPieceN = PieceHolder.generateRandomPiece();

				}

			}
			
			int score1;
			score1 = logic.deleteMatrix(backMatrix, score);
			if(score1 != score){
				score = score1;
				Constants.isMusicOn = true;
				sound.SoundIt();
				Constants.isMusicOn = false;
			}
			if (score > FileIO.convertDate(playerScore, 0)) {
				highScore = score;

			}

		} else {
			if (score > FileIO.convertDate(playerScore, 9)) {

				if (printJustOnce == 0) {
					newRecord(score);
					printJustOnce++;
				}

			}
		}

	}

}