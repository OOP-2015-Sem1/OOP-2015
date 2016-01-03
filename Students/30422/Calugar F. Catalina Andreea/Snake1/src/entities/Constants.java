package entities;

import java.awt.Color;

import javax.swing.border.LineBorder;

public class Constants {

	public static final String UP = "UP";
	public static final String DOWN = "DOWN";
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String STEP = "STEP";

	public static final int MIN_BOARD_SIZE = 10;
	public static final int MAX_BOARD_SIZE = 20;
	public static final int DEFAULT_BOARD_SIZE = 20;
	public static final int DIMENSION = 800;

	public static final LineBorder LINE_BORDER = new LineBorder(Color.GRAY);

	public static boolean canWeGoThroughWalls;
	public static int BOARD_SIZE = 20;

}
