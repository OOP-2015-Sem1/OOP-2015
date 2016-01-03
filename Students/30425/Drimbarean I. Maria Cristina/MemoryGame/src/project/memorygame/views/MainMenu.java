package project.memorygame.views;

import project.memorygame.controllers.*;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {

	private JButton play;
	private JButton quit;
	private Game game;

	public MainMenu(int width, int height, Game game) {
		this.game = game;
		this.setTitle("Main Menu");
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new GridLayout(5, 3));

		JButton buttons[] = new JButton[13];

		for (int i = 0; i <= 12; i++) {
			buttons[i] = new JButton();
			this.add(buttons[i]);
			buttons[i].setEnabled(false);
			// buttons[i].setVisible(false);
			buttons[i].setBackground(Color.YELLOW);
			buttons[i].setBorderPainted(false);
			if (i == 3) {
				play = new JButton("Play");
				play.setBackground(Color.CYAN);
				this.add(play);
				play.setOpaque(true);
				play.setEnabled(true);
			}
			if (i == 8) {
				quit = new JButton("Quit");
				quit.setBackground(Color.CYAN);
				this.add(quit);
				quit.setOpaque(true);
				quit.setEnabled(true);
			}

		}
		Handler handler = new Handler();
		play.addActionListener(handler);
		quit.addActionListener(handler);
	}

	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == play) {
				game.enterDifficultyMenu();
			} else if (event.getSource() == quit)
				System.exit(0);
		}
	}
}