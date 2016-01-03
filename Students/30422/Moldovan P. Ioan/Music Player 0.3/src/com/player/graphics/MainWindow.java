package com.player.graphics;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainWindow {
	public static final long serialVersionUID = 1L;
	private JFrame window;
	private MainWindowLeft leftSide;
	private MainWindowRight rightSide;
	private UpperMenuBar menuBar;

	public MainWindow() {
		window = new JFrame();
		window.setTitle("Music Player 0.3 Alpha");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(800, 600);
		window.setLayout(new GridLayout(1, 2));
		menuBar = new UpperMenuBar();
		window.setJMenuBar(menuBar);
		leftSide = new MainWindowLeft();
		window.add(leftSide);
		rightSide = new MainWindowRight();
		window.add(rightSide);
		window.setVisible(true);
	}

	public static void closeProgram() {
		int option;
		option = JOptionPane.showConfirmDialog(null, "Are you sure you wanna exit?", "Confirm Exit",
				JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
}
