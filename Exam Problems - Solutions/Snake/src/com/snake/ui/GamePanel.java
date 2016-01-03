package com.snake.ui;

import static com.snake.ui.SnakeFrame.BOARD_SIZE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.snake.core.Constants;
import com.snake.model.Apple;
import com.snake.model.Snake;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -6485443903064696099L;
	public JLabel[][] squares;

	public GamePanel() {

		setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
		setPreferredSize(new Dimension(550, 700));

		squares = new JLabel[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				squares[i][j] = new JLabel();
				squares[i][j].setBackground(Color.LIGHT_GRAY);
				squares[i][j].setOpaque(true);
				squares[i][j].setBorder(Constants.LINE_BORDER);
				add(squares[i][j]);
			}
		}
	}

	public void updateBoard(Snake snake, Apple apple) {
		resetBoard();
		ArrayList<Point> positions = snake.getPositions();
		for (int i = 0; i < snake.getLength(); i++) {
			if (i == 0) {
				// special case for head
				squares[positions.get(i).x][positions.get(i).y]
						.setBackground(Color.yellow);
			} else
				squares[positions.get(i).x][positions.get(i).y]
						.setBackground(Color.GREEN);
		}
	}

	public void resetBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				squares[i][j].setBackground(Color.LIGHT_GRAY);
			}
		}
	}

	public void setApple(Apple apple) {
		squares[apple.x][apple.y].setBackground(Color.RED);
	}
}
