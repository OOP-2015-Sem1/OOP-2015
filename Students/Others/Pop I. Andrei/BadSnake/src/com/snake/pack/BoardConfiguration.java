package com.snake.pack;

import java.awt.Color;
import javax.swing.*;
import java.awt.Point;
import java.util.ArrayList;

import com.snake.components.ControlPanel;
import com.snake.models.Apple;
import com.snake.models.Snake;

public class BoardConfiguration { //  here I wanted to implement all the things related to the board 
	
	private int boardSize;
	private ControlPanel theControlPanel;
	private Apple apple;
	private Snake snake;
	private JLabel[][] scores;
	private int actualScore = 0;
	
	public BoardConfiguration(int boardSize, Apple apple, Snake snake, ControlPanel controlPanel, JLabel[][] scores) {
		this.boardSize = boardSize;
		this.apple = apple;
		this.snake = snake;
		this.theControlPanel = controlPanel;
		this.scores = scores;
	}
	
	public void updateBoard(Snake snake, Apple apple) {
		modifyBackground();
		ArrayList<Point> positions = snake.getTheSnake();
		for (int i = 0; i < snake.getLength(); i++) {
			if (i == 0) {
				// special case for head
				scores[positions.get(i).x][positions.get(i).y]
						.setBackground(Color.yellow);
			} else
				scores[positions.get(i).x][positions.get(i).y]
						.setBackground(Color.GREEN);
		}
	}
	
	public void modifyBackground() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				scores[i][j].setBackground(Color.LIGHT_GRAY);
			}
		}
	}
	
	public void setApple(Apple apple) {
		scores[apple.x][apple.y].setBackground(Color.RED);
	}

	public void updateBoard() {
		updateBoard(snake, apple);

		if (snake.headIsEatingApple(apple)) {
			actualScore++;
			apple.generateNewPosition(snake);
		}

		setApple(apple);
		theControlPanel.setTheScore(actualScore);
	}
	
}
