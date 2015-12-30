package game;

public class Constants {

	public static final int COLOR_WHITE = 0;
	public static final int COLOR_RED = 1;

	public static final int SQUARE_WIDTH = 44;
	public static final int SQUARE_HEIGHT = 24;

	public static final int PIECES_START_X = 88;
	public static final int PIECES_START_Y = 23;

	public static final int DRAG_TARGET_SQUARE_START_X = PIECES_START_X - (int) (SQUARE_WIDTH / 2.0);
	public static final int DRAG_TARGET_SQUARE_START_Y = PIECES_START_Y - (int) (SQUARE_HEIGHT / 2.0);
	
	public static final int GAME_STATE_WHITE = 0;
	public static final int GAME_STATE_RED = 1;
	public static final int GAME_STATE_END = 2;
}
