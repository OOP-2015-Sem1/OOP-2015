package com.snake.core;

import static com.snake.core.Constants.DOWN;
import static com.snake.core.Constants.LEFT;
import static com.snake.core.Constants.RIGHT;
import static com.snake.core.Constants.UP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.snake.ui.SnakeFrame;

public class SnakeController implements GameObserver {

	private SnakeFrame snakeFrame;

	public void runSnakeGame() {
		snakeFrame = new SnakeFrame();
		snakeFrame.addKeyListener(new SnakeKeyListener());
		snakeFrame
				.addActionListenerToButtons(new SnakeMouseControllsClickListener());
		snakeFrame.addGameObserver(this);
	}

	private class SnakeKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch (keyCode) {
			case KeyEvent.VK_UP:
				snakeFrame.moveSnake(UP);
				break;
			case KeyEvent.VK_DOWN:
				snakeFrame.moveSnake(DOWN);
				break;
			case KeyEvent.VK_LEFT:
				snakeFrame.moveSnake(LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				snakeFrame.moveSnake(RIGHT);
				break;
			case KeyEvent.VK_R:
				resetGame();
				break;

			}
		}

		private void resetGame() {
			snakeFrame.setVisible(false);
			snakeFrame.dispose();
			runSnakeGame();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}

	private class SnakeMouseControllsClickListener implements ActionListener {

		private JButton[][] controlButtons;

		public SnakeMouseControllsClickListener() {
			// point to the same JButton matrix -> almost no additional memory
			this.controlButtons = snakeFrame.getControlButtons();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == controlButtons[0][1]) {
				snakeFrame.moveSnake(Constants.UP);
			}
			if (e.getSource() == controlButtons[1][1]) {
				snakeFrame.moveSnake(Constants.STEP);
			}
			if (e.getSource() == controlButtons[1][2]) {
				snakeFrame.moveSnake(Constants.RIGHT);
			}
			if (e.getSource() == controlButtons[1][0]) {
				snakeFrame.moveSnake(Constants.LEFT);
			}
			if (e.getSource() == controlButtons[2][1]) {
				snakeFrame.moveSnake(Constants.DOWN);
			}
		}

	}

	@Override
	public void notifyLoss() {
		JOptionPane.showMessageDialog(null, "You lost.");
		System.exit(0);
	}

	@Override
	public void notifyWin() {
		JOptionPane.showMessageDialog(null, "You won!");
		System.exit(0);
	}
}
