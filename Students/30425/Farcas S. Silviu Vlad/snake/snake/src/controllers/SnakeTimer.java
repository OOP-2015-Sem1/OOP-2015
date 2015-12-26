package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import models.*;

public class SnakeTimer implements ActionListener {

	public Timer timer = new Timer(20, this);
	private Snake snake;
	private GameManagement game;

	public SnakeTimer(GameManagement game, Snake snake) {
		this.game=game;
		this.snake=snake;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		game.getRenderPanel().repaint();
		
		snake.direction(game);

		if (game.getOver()) {
			timer.stop();
		}
	}

}
