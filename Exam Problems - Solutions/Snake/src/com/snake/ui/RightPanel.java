package com.snake.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = 2713792816194081724L;

	public RightPanel(ControlsPanel controlsPanel) {
		setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == 1 && j == 1)
					add(controlsPanel);
				else
					add(new JLabel());
			}
		}
	}
}
