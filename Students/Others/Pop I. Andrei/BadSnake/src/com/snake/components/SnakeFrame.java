package com.snake.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.snake.models.Apple;
import com.snake.models.Snake;
import com.snake.pack.BoardConfiguration;
import com.snake.pack.Constants;

public class SnakeFrame extends JFrame {

	private static final long serialVersionUID = -8573649221379461824L;

	public static int BOARD_SIZE;

	private GamePanel gamePanel;
	private ControlPanel controlPanel;

	public JLabel[][] scores;
	//private JButton[][] controlButtons = new JButton[3][3];

	private Apple apple;
	private Snake snake;
	
	private final BoardConfiguration boardConfiguration;
	
	public SnakeFrame() {
		super("Snake!");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000, 700));

		BOARD_SIZE = queryForBoardSize();
		boolean canWeGoThroughWalls = queryForGoingThroughWalls();

		gamePanel = new GamePanel(BOARD_SIZE, this);
		
		scores = new JLabel[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				scores[i][j] = new JLabel();
				scores[i][j].setBackground(Color.LIGHT_GRAY);
				scores[i][j].setOpaque(true);
				scores[i][j].setBorder(Constants.LINE_BORDER);
				gamePanel.add(scores[i][j]);
			}
		}

		controlPanel = new ControlPanel(this);

		snake = new Snake(canWeGoThroughWalls);
		apple = new Apple();
		apple.generateNewPosition(snake);
		
		boardConfiguration = new BoardConfiguration(BOARD_SIZE, apple, snake, controlPanel, scores);
		boardConfiguration.updateBoard();

		add(gamePanel, BorderLayout.WEST);
		JPanel rightControlsPanel = setupRightControlsPanel();
		add(rightControlsPanel, BorderLayout.CENTER);

		//setFocusable(true);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel setupRightControlsPanel() {
		JPanel rightBoard = new JPanel();
		rightBoard.setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1)
					rightBoard.add(controlPanel);
				else
					rightBoard.add(new JLabel());
			}
		}
		return rightBoard;
	}

	public void showMessage(String direction) {
		if (snake.getLength() == BOARD_SIZE * BOARD_SIZE - 1) {
			JOptionPane.showMessageDialog(null, "You won!");
			System.exit(0);
		}

		if (!snake.move(direction, apple)) {
			JOptionPane.showMessageDialog(null, "You lost");
			System.exit(0);
		} else {
			boardConfiguration.updateBoard();
		}
	}

	public static int queryForBoardSize() {
		String size = JOptionPane.showInputDialog("Dimensiunea tablei: (7-30)");

		Integer boardSize;
		try {
			boardSize = Integer.parseInt(size);
			if (boardSize < 7 || boardSize > 30)
				boardSize = 15;
		} catch (NumberFormatException e) {
			boardSize = 15;
		}
		return boardSize;
	}

	public static boolean queryForGoingThroughWalls() {
		int reply = JOptionPane.showConfirmDialog(null,
				"Sa treaca prin pereti?", "Optiune", JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}

}
