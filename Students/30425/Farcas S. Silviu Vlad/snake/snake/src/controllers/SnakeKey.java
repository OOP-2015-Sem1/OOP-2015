package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import models.*;
import controllers.*;
import views.*;

public class SnakeKey implements KeyListener {

	private Snake snake;
	
	public SnakeKey(){
		snake=Snake.getSnake();
	}
	
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && snake.direction != Snake.RIGHT) {
			snake.direction = Snake.LEFT;
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && snake.direction != Snake.LEFT) {
			snake.direction = Snake.RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && snake.direction != Snake.DOWN) {
			snake.direction = Snake.UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && snake.direction != Snake.UP) {
			snake.direction = Snake.DOWN;
		}

		if (i == KeyEvent.VK_SPACE) {
			if (snake.getOver()) {
				snake.getJframe().dispose();
			} else {
				snake.setPaused(!snake.getPaused());
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
