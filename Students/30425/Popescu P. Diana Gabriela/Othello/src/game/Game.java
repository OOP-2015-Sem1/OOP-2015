package game;

import io.OthelloFrame;

public class Game {

	public BoardConfiguration boardConfiguration;
	public OthelloFrame othelloFrame;

	public Game() {
		boardConfiguration = new BoardConfiguration();
		othelloFrame = new OthelloFrame();
	}

	public void run() {
		new Controller(othelloFrame, boardConfiguration);
	}

}
