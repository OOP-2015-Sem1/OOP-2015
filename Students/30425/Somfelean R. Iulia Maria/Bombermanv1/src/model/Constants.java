package model;

public class Constants {

	public static final int GAME_ELEMENT_SIZE_X = 65;
	public static final int GAME_ELEMENT_SIZE_Y = 58;
	// values from matrix read from file, used for initializing the game
	public static final int EMPTY_SQUARE = 0;
	public static final int WALL_UNBREAKABLE = 1;
	public static final int WALL_BREAKABLE_EMPTY = 2;
	public static final int WALL_BREAKABLE_SPEED_POWER_UP = 3;
	// ...........

	public static final int BLOCK_COUNT_X = 10;
	public static final int BLOCK_COUNT_Y = 10;

	public static final String matrixFileName = "initialMatrix.txt";
	public static final String resourcesDir = "Resources\\";

	// initial position for Bomberman

	public static final int BOMBERMAN_STEP_SIZE = 7;
	public static final int bombermanXInitPos = GAME_ELEMENT_SIZE_X;
	public static final int bombermanYInitPos = GAME_ELEMENT_SIZE_Y;

	public static final int BOMBERMAN_WIDTH = 64; // the width of the image
	public static final int BOMBERMAN_HEIGHT = 72; // the height of the image

	public static final int STATUS_STANDING = 0;
	public static final int STATUS_MOVING_UP = 1;
	public static final int STATUS_MOVING_DOWN = 2;
	public static final int STATUS_MOVING_LEFT = 3;
	public static final int STATUS_MOVING_RIGHT = 4;

}
