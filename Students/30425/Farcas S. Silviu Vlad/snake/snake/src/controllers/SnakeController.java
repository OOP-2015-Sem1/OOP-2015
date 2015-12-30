package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import models.*;

public class SnakeController implements KeyListener {

	private Snake snake;
	private GameManagement game;

	public SnakeController(Snake snake, GameManagement game) {
		this.snake = snake;
		this.game = game;
	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && snake.getDirection() != Snake.RIGHT) {
			snake.setDirection(Snake.LEFT);
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && snake.getDirection() != Snake.LEFT) {
			snake.setDirection(Snake.RIGHT);
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && snake.getDirection() != Snake.DOWN) {
			snake.setDirection(Snake.UP);
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && snake.getDirection() != Snake.UP) {
			snake.setDirection(Snake.DOWN);
		}

		if (i == KeyEvent.VK_SPACE) {
			if (game.getOver()) {
				game.getGameFrame().dispose();
			} else {
				game.setPaused(!game.getPaused());
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
