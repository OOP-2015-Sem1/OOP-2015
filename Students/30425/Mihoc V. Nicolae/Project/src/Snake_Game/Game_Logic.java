package Snake_Game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

import Clock.Time;
import Constants.Direction;
import Constants.TileType;
import Game_Characters.Fruit;
import Game_Characters.Snake;
import Panels.BoardPanel;
import Panels.SidePanel;

public class Game_Logic {
	private Random random;
	private LinkedList<Point> snake;
	private LinkedList<Direction> directions;
	private Time logicTimer;
	private boolean isNewGame;
	private BoardPanel board;
	private SidePanel side;
	private static long FRAME_TIME = 6000 / 50;
	private Snake snake2;
	private Fruit apple;
	private int applesEaten;
	private boolean isGameOver;
	
	public void startGame() {

		this.setRandom(new Random());
		this.snake = new LinkedList<>();
		this.directions = new LinkedList<>();
		this.logicTimer = new Time(9.0f);
		this.setNewGame(true);
		
		
		logicTimer.setPaused(true);

		while (true) {
			long start = System.nanoTime();
			logicTimer.update();

			if (logicTimer.hasElapsedCycle()) {
				updateGame();
			}

			board.repaint();
			side.repaint();

			long delta = (System.nanoTime() - start) / 1000000L;
			if (delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateGame() {
		TileType collision = snake2.updateSnake();

		if (collision == TileType.Apple) {
			setApplesEaten(getApplesEaten() + 1);
			apple.spawnFruit();
		} else if (collision == TileType.SnakeBody) {
			setGameOver(true);
			logicTimer.setPaused(true);
		}
	}

	

	public void resetGame() {
		
		this.setApplesEaten(0);

		this.setNewGame(false);
		this.setGameOver(false);

		Point head = new Point(BoardPanel.COL / 2, BoardPanel.ROW / 2);

		snake.clear();
		snake.add(head);

		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);

		directions.clear();
		directions.add(Direction.UP);

		logicTimer.reset();

		apple.spawnFruit();
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public boolean isNewGame() {
		return isNewGame;
	}

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public int getApplesEaten() {
		return applesEaten;
	}

	public void setApplesEaten(int applesEaten) {
		this.applesEaten = applesEaten;
	}
}
