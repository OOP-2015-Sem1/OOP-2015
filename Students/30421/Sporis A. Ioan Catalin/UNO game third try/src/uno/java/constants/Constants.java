package uno.java.constants;

import uno.java.GUI.GameHandler;

public abstract class Constants {
	public static final String START_NEW_GAME = "Start New Game";
	public static final String EXIT_GAME = "Exit Game";
	public static final String HELP = "Help";
	public static final String NR_OF_PLAYERS_MESSAGE = "Please insert the number of players!";
	public static final String[] PLAYERS = { "2", "3", "4" };
	public static final String ConfirmBtn = "OK";
	public static final int FIRST_HAND = 7;

	public static final String RED = "red";
	public static final String BLUE = "blue";
	public static final String GREEN = "green";
	public static final String YELLOW = "yellow";

	public static final String SKIP = "Skip";
	public static final String WILD = "Wild";
	public static final String WILDRAW = "WildDraw4Cards";
	public static final String DRAW2 = "Draw2";
	public static final String REVERSE = "Reverse";
	public static GameHandler gameHandler = new GameHandler();
}
