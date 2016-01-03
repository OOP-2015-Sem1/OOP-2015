package core;

import model.Bomberman;
import model.BonusType;
import model.Constants;
import model.OutOfMapException;
import model.Wall;

import java.io.*;
import java.util.Scanner;

public class GameManagerImpl implements GameManager {

	private Wall[][] board;
	private Bomberman bomberman;
	private int[][] initialGameMatrix;
	// private ArrayList<Bomb> bombsList;
	// private ArrayList<Explosion> explosionsList;
	// private ArrayList<Angel> angelList;

	// receive matrix from file or read here
	public GameManagerImpl(String fileName) throws OutOfMapException {

		initialGameMatrix = readMatrix(fileName);
		printMatrix(initialGameMatrix);
		board = new Wall[Constants.BLOCK_COUNT_X][Constants.BLOCK_COUNT_Y];
		for (int i = 0; i < Constants.BLOCK_COUNT_X; i++) {
			for (int j = 0; j < Constants.BLOCK_COUNT_Y; j++) {

				switch (initialGameMatrix[i][j]) {

				case Constants.EMPTY_SQUARE:
					board[i][j] = new Wall(j * Constants.GAME_ELEMENT_SIZE_X, i * Constants.GAME_ELEMENT_SIZE_Y, false,
							false, BonusType.EMPTY);
					break;
				case Constants.WALL_UNBREAKABLE:
					board[i][j] = new Wall(j * Constants.GAME_ELEMENT_SIZE_X, i * Constants.GAME_ELEMENT_SIZE_Y, false,
							false, null);
					break;
				case Constants.WALL_BREAKABLE_EMPTY:
					board[i][j] = new Wall(j * Constants.GAME_ELEMENT_SIZE_X, i * Constants.GAME_ELEMENT_SIZE_Y, true,
							false, null);
					break;
				case Constants.WALL_BREAKABLE_SPEED_POWER_UP:
					board[i][j] = new Wall(j * Constants.GAME_ELEMENT_SIZE_X, i * Constants.GAME_ELEMENT_SIZE_Y, true,
							true, BonusType.SPEED_UP);
					break;
				default:
					board[i][j] = new Wall(j * Constants.GAME_ELEMENT_SIZE_X, i * Constants.GAME_ELEMENT_SIZE_Y, false,
							false, null);
					break;
				}

			}
		}

		bomberman = new Bomberman(Constants.bombermanXInitPos, Constants.bombermanYInitPos);

	}

	@Override
	public void moveUpBomberman() {

		int distance = bomberman.getSpeed() * Constants.BOMBERMAN_STEP_SIZE;
		if (getElementFromMatrixByCoordinate(bomberman.getxPosition() + Constants.BOMBERMAN_WIDTH / 2,
				bomberman.getyPosition() - distance) == Constants.EMPTY_SQUARE) {
			bomberman.setxPosition(bomberman.getxPosition());
			bomberman.setyPosition(bomberman.getyPosition() - distance);

		}

	}

	@Override
	public void moveDownBomberman() {

		int distance = bomberman.getSpeed() * Constants.BOMBERMAN_STEP_SIZE;
		if (getElementFromMatrixByCoordinate(bomberman.getxPosition() + Constants.BOMBERMAN_WIDTH / 2,
				bomberman.getyPosition() + Constants.BOMBERMAN_HEIGHT + distance) == Constants.EMPTY_SQUARE) {

			bomberman.setxPosition(bomberman.getxPosition());
			bomberman.setyPosition(bomberman.getyPosition() + distance);
		}

	}

	@Override
	public void moveLeftBomberman() {

		int distance = bomberman.getSpeed() * Constants.BOMBERMAN_STEP_SIZE;
		if (getElementFromMatrixByCoordinate(bomberman.getxPosition() - distance,
				bomberman.getyPosition() + Constants.BOMBERMAN_HEIGHT / 2) == Constants.EMPTY_SQUARE) {
			bomberman.setxPosition(bomberman.getxPosition() - distance);
			bomberman.setyPosition(bomberman.getyPosition());
		}

	}

	@Override
	public void moveRightBomberman() {

		int distance = bomberman.getSpeed() * Constants.BOMBERMAN_STEP_SIZE;
		if (getElementFromMatrixByCoordinate(bomberman.getxPosition() + Constants.BOMBERMAN_WIDTH + distance,
				bomberman.getyPosition() + Constants.BOMBERMAN_HEIGHT / 2) == Constants.EMPTY_SQUARE) {

			bomberman.setxPosition(bomberman.getxPosition() + distance);
			bomberman.setyPosition(bomberman.getyPosition());
		}
	}

	@Override
	public void placeBomb() {
		// TODO Auto-generated method stub

	}

	/**
	 * Reads a 2D array
	 * 
	 * @param fileName
	 *            the name of the file which contains the matrix
	 * @return the matrix read from file
	 */
	public static int[][] readMatrix(String fileName) {
		int[][] a = new int[Constants.BLOCK_COUNT_X][Constants.BLOCK_COUNT_Y];
		try {
			Scanner input = new Scanner(new File(fileName));

			// read in the data
			input = new Scanner(new File(fileName));
			for (int i = 0; i < Constants.BLOCK_COUNT_X; i++) {
				for (int j = 0; j < Constants.BLOCK_COUNT_Y; j++) {
					if (input.hasNextInt()) {
						a[i][j] = input.nextInt();
					}
				}
			}
			input.close();
			return a;

		} catch (IOException e) {
			System.out.println("File Not Found " + e);
			return null;
		}

	}

	/**
	 * Prints a 2D Array on the sceen
	 * 
	 * @param m
	 */
	public static void printMatrix(int[][] m) {
		for (int i = 0; i < Constants.BLOCK_COUNT_X; i++) {
			for (int j = 0; j < Constants.BLOCK_COUNT_Y; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}

	public Wall[][] getBoard() {
		return board;
	}

	public Bomberman getBomberman() {
		return bomberman;
	}

	/*
	 * gets the matrix element from line and column (x, y)
	 **/
	public int getElementFromMatrix(int x, int y) {
		// if( x > 0 && x< Constants.BLOCK_COUNT_X && y> 0 &&
		// y<Constants.BLOCK_COUNT_Y)
		return initialGameMatrix[x][y];

	}

	public int getElementFromMatrixByCoordinate(int xPosition, int yPosition) {
		int rowPosition = (yPosition) / Constants.GAME_ELEMENT_SIZE_Y;
		int columnPosition = (xPosition) / Constants.GAME_ELEMENT_SIZE_X;

		return initialGameMatrix[rowPosition][columnPosition];
	}

	/*
	 * sets the matrix element from coordinates (x, y)
	 **/
	public void setElementMatrix(int x, int y, int elem) {
		initialGameMatrix[x][y] = elem;

	}

	/*
	 * the position of the bomberman is refreshed taking into account the status
	 */
	public void refreshBomberman() {
		if (bomberman.getStatus() == Constants.STATUS_MOVING_UP) {
			System.out.println("i'm moving up");
			moveUpBomberman();
		}
		if (bomberman.getStatus() == Constants.STATUS_MOVING_DOWN) {
			System.out.println("i'm moving down");
			moveDownBomberman();
		}
		if (bomberman.getStatus() == Constants.STATUS_MOVING_LEFT) {
			System.out.println("i'm moving left");
			moveLeftBomberman();
		}
		if (bomberman.getStatus() == Constants.STATUS_MOVING_RIGHT) {
			System.out.println("i'm moving right");
			moveRightBomberman();
		}
		;
	}
}
