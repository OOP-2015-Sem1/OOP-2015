package game;

import io.DimensionOfBoardFrame;

public class Game {

	public DimensionOfBoardFrame dimensionOfBoardFrame;

	public Game() {
		dimensionOfBoardFrame = new DimensionOfBoardFrame();
	}

	public void run() {
		new Controller(dimensionOfBoardFrame);
	}

}
