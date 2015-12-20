package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import models.*;
import views.*;

public class SnakeKey implements KeyListener {

	private Snake snake;
	private SnakeDirection snakeDirection;
	private GameManagement game;

	public SnakeKey() {
		snake = Snake.getSnake();
		snakeDirection = SnakeDirection.getSnakeDirection();
		game=GameManagement.getGame();
	}

	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && snakeDirection.getDirection() != SnakeDirection.RIGHT) {
			snakeDirection.setDirection(SnakeDirection.LEFT);
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && snakeDirection.getDirection() != SnakeDirection.LEFT) {
			snakeDirection.setDirection(SnakeDirection.RIGHT);
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && snakeDirection.getDirection() != SnakeDirection.DOWN) {
			snakeDirection.setDirection(SnakeDirection.UP);
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && snakeDirection.getDirection() != SnakeDirection.UP) {
			snakeDirection.setDirection(SnakeDirection.DOWN);
		}

		if (i == KeyEvent.VK_SPACE) {
			if (game.getOver()) {
				RenderPanel.jframe.dispose();
			} else {
				game.setPaused(!game.getPaused());
			}
		}
		SnakeDirection.setSnakeDirection(snakeDirection);
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
