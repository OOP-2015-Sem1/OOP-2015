package com.snake.components;

import javax.swing.*;

import com.snake.pack.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.snake.pack.Constants;

public class GamePanel extends JPanel implements KeyListener{
	
	private SnakeFrame snakeFrame;
	
	public GamePanel(int BOARD_SIZE, SnakeFrame snakeFrame) {
		
		setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
		setPreferredSize(new Dimension(550, 700));
		addKeyListener(this);
		setFocusable(true);
		this.snakeFrame = snakeFrame;
	}
	
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			snakeFrame.showMessage(Constants.UP);
			break;
		case KeyEvent.VK_DOWN:
			snakeFrame.showMessage(Constants.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			snakeFrame.showMessage(Constants.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			snakeFrame.showMessage(Constants.RIGHT);
			break;
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
