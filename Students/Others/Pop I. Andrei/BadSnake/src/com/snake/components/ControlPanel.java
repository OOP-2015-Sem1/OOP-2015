package com.snake.components;

import javax.swing.*;

import com.snake.pack.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener{
	
	private SnakeFrame snakeFrame;
	private JButton[][] controlButtons = new JButton[3][3];
	
	public ControlPanel(SnakeFrame snakeFrame){ 
		setLayout(new GridLayout(3, 3));
		this.snakeFrame = snakeFrame;
		
		for(int i = 0; i<3; i++)
			for(int j = 0; j<3; j++) {
				controlButtons[i][j] = createNewButton(controlButtons[i][j], this);
				add(controlButtons[i][j]);
			}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i % 2 == 0 && j % 2 == 0) {
					controlButtons[i][j].setEnabled(false);
					controlButtons[i][j].setBackground(Color.WHITE);
				} else {
					controlButtons[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
		}

		controlButtons[0][1].setText(Constants.UP);
		controlButtons[1][0].setText(Constants.LEFT);
		controlButtons[1][1].setText(Constants.STEP);
		controlButtons[1][2].setText(Constants.RIGHT);
		controlButtons[2][1].setText(Constants.DOWN);

	}
	
	private JButton createNewButton(JButton myButton, ActionListener handler) {
		myButton = new JButton();
		myButton.setBorder(Constants.LINE_BORDER);
		myButton.addActionListener(handler);
		
		return myButton;
	}
	
	public void setTheScore(int theNewScore) {
		controlButtons[0][0].setText(String.valueOf(theNewScore));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == controlButtons[0][1]) {
			snakeFrame.showMessage(Constants.UP);
		}
		if (e.getSource() == controlButtons[1][1]) {
			snakeFrame.showMessage(Constants.STEP);
		}
		if (e.getSource() == controlButtons[1][2]) {
			snakeFrame.showMessage(Constants.RIGHT);
		}
		if (e.getSource() == controlButtons[1][0]) {
			snakeFrame.showMessage(Constants.LEFT);
		}
		if (e.getSource() == controlButtons[2][1]) {
			snakeFrame.showMessage(Constants.DOWN);
		}
	}

}
