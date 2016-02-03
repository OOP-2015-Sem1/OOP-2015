package com.snake.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.snake.core.Constants;

public class ControlsPanel extends JPanel {

	private static final long serialVersionUID = 7142658375945789569L;

	private JButton[][] controlButtons;

	public ControlsPanel() {
		setLayout(new GridLayout(3, 3));

		controlButtons = new JButton[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				controlButtons[i][j] = new JButton();
				controlButtons[i][j].setBorder(Constants.LINE_BORDER);
				add(controlButtons[i][j]);
			}
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

	public void setScore(int score) {
		controlButtons[0][0].setText(String.valueOf(score));
	}

	public JButton[][] getControlButtons() {
		return this.controlButtons;
	}

	public void addActionListenerToButtons(ActionListener actionListener) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				controlButtons[i][j].addActionListener(actionListener);
			}
		}
	}

}
