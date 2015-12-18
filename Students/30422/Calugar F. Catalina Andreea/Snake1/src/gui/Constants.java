package gui;

import java.awt.Color;

import javax.swing.border.LineBorder;

public class Constants {

	public static final String UP = "UP";
	public static final String DOWN = "DOWN";
	public static final String LEFT = "LEFT";
	public static final String RIGHT = "RIGHT";
	public static final String STEP = "STEP";

	public static final LineBorder LINE_BORDER = new LineBorder(Color.GRAY);
	public static boolean canWeGoThroughWalls;

	public static boolean right = true;
	public static boolean left = false;
	public static boolean up = false;
	public static boolean down = false;
}
