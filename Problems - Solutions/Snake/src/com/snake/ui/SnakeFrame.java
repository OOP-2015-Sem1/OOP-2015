package com.snake.ui;

import static com.snake.core.Constants.FRAME_HEIGHT;
import static com.snake.core.Constants.FRAME_WIDTH;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.snake.core.GameObserver;
import com.snake.model.Apple;
import com.snake.model.Snake;

public class SnakeFrame extends JFrame {

	private static final long serialVersionUID = -8573649221379461824L;

	public static int BOARD_SIZE;

	private GamePanel gamePanel;
	private ControlsPanel controlPanel;

	private Apple apple;
	private Snake snake;

	private int score = 0;

	public SnakeFrame() {
		super("Snake!");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(FRAME_HEIGHT, FRAME_WIDTH));

		BOARD_SIZE = SettingsDialogs.queryForBoardSize();
		boolean canWeGoThroughWalls = SettingsDialogs
				.queryForGoingThroughWalls();

		gamePanel = new GamePanel();
		controlPanel = new ControlsPanel();

		snake = new Snake(canWeGoThroughWalls);
		apple = new Apple();
		apple.generateNewPosition(snake);
		updateBoard();

		add(gamePanel, BorderLayout.WEST);
		add(new RightPanel(controlPanel), BorderLayout.CENTER);

		setFocusable(true);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void moveSnake(String direction) {
		snake.move(direction, apple);
		updateBoard();
		requestFocus();
	}

	public JButton[][] getControlButtons() {
		return this.controlPanel.getControlButtons();
	}

	public void addActionListenerToButtons(ActionListener actionListener) {
		controlPanel.addActionListenerToButtons(actionListener);
	}

	public void addGameObserver(GameObserver gameObserver) {
		snake.setGameObserver(gameObserver);
	}

	private void updateBoard() {
		gamePanel.updateBoard(snake, apple);

		if (snake.headIsEatingApple(apple)) {
			score++;
			apple.generateNewPosition(snake);
		}

		gamePanel.setApple(apple);
		controlPanel.setScore(score);
	}

}
