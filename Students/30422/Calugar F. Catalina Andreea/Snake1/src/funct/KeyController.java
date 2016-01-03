package funct;

import static entities.Constants.DOWN;
import static entities.Constants.LEFT;
import static entities.Constants.RIGHT;
import static entities.Constants.UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gui.Snake;

public class KeyController implements KeyListener {

	Snake snake = new Snake();

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			snake.moveSnake(RIGHT);

		} else if (key == KeyEvent.VK_LEFT) {
			snake.moveSnake(LEFT);

		} else if (key == KeyEvent.VK_UP) {
			snake.moveSnake(UP);

		} else if (key == KeyEvent.VK_DOWN) {
			snake.moveSnake(DOWN);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	
}
