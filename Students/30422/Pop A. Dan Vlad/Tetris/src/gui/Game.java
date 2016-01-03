package gui;

import java.awt.*;

import javax.swing.*;

import controller.Main;

public class Game {
	public static JFrame jframe;
	private JPanel mainPanel;
	public static JPanel game;
	public JPanel stats;
	private static JButton board[][];
	private static JTextField score;

	public Game() {

		jframe = new JFrame();
		jframe.setSize(500, 700);
		jframe.setTitle("Tetris");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		score = new JTextField();
		game = new JPanel();
		game.setLayout(new GridLayout(10, 11));
		board = new JButton[10][11];
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 11; j++) {
				board[i][j] = new JButton();
				if (i == 9 || j == 0 || j == 10) {
					board[i][j].setBackground(Color.BLACK);
				} else {
					board[i][j].setBackground(Color.WHITE);
				}

				game.add(board[i][j]);
			}

		score = new JTextField("0");

		mainPanel.add(game, BorderLayout.CENTER);
		mainPanel.add(score, BorderLayout.NORTH);
		jframe.add(mainPanel);
		game.setVisible(true);
		mainPanel.setVisible(true);
		jframe.setVisible(false);
		game.setFocusable(true);
		game.requestFocusInWindow();

	}

	public JPanel getGame() {
		return game;
	}

	public static void repaint() {

		for (int i = 0; i < 9; i++)
			for (int j = 1; j < 10; j++) {
				if (Main.matrice[i][j + 2] == 0) {
					board[i][j].setBackground(Color.WHITE);
				}
				if (Main.matrice[i][j + 2] == 11 || Main.matrice[i][j + 2] == 111) {
					board[i][j].setBackground(Color.RED);
				}
				if (Main.matrice[i][j + 2] == 12 || Main.matrice[i][j + 2] == 112) {
					board[i][j].setBackground(Color.BLUE);
				}
				if (Main.matrice[i][j + 2] == 13 || Main.matrice[i][j + 2] == 113) {
					board[i][j].setBackground(Color.GREEN);
				}
				if (Main.matrice[i][j + 2] == 14 || Main.matrice[i][j + 2] == 114) {
					board[i][j].setBackground(Color.YELLOW);
				}
				if (Main.matrice[i][j + 2] == 200) {
					board[i][j].setBackground(Color.BLACK);
				}
			}
		score.setText("" + Main.score);
	}
}
