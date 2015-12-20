package controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import models.*;
import views.*;

public class SnakeTimer implements ActionListener {

	public Timer timer = new Timer(20, this);
	private Snake snake;
	private SnakeDirection snakeDirection;
	private GameManagement game;

	public SnakeTimer() {
		snake = Snake.getSnake();
		snakeDirection = SnakeDirection.getSnakeDirection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		snake.repaintRenderPanel();

		snake = Snake.getSnake();
		
		game=GameManagement.getGame();
		
		snakeDirection.direction();

		if (game.getOver()) {
			timer.stop();
		}
		Snake.setSnake(snake);
	}

}
