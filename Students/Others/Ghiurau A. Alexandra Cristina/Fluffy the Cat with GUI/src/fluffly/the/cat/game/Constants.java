package fluffly.the.cat.game;

import java.awt.Color;

import javax.swing.border.LineBorder;

public class Constants {

	// BOARD CONTROLERS

	public static final String UP = "w";
	public static final String DOWN = "s";
	public static final String LEFT = "a";
	public static final String RIGHT = "d";

	public static final LineBorder LINE_BORDER = new LineBorder(Color.BLACK);
	
	public static final Integer FRAME_HEIGHT = 1000;
	public static final Integer FRAME_WIDTH = 800;
	
	public static final int SPEED = 1;

	// BOARD CONFIGURATION

	public static final int MAX_VIEW_DISTANCE = 15;

	public static final int ROWS = 20;
	public static final int COLS = 40;
	public final static char[][] board = new char[ROWS][COLS];

	public static final String CONFIG_FILE_NAME = "FluffyWorld.txt";
	public static final String FLUFFY_HAT_DAT = "FluffyHat.dat";

	public static final String RESTORE = "r";
	public static final String SAVE = "s";

	public static final String QUIT = "q";
}
