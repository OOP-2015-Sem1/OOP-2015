package project.memorygame.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import project.memorygame.controllers.Game;

@SuppressWarnings("serial")
public class WinMenu extends JFrame {

	public Game game;
	private JButton playAgain;
	private JButton quit;

	public WinMenu(int width, int height, Game game) {
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
		this.setLayout(new GridLayout(2, 5));

		JButton buttons[] = new JButton[8];

		for (int i = 0; i <= 7; i++) {
			buttons[i] = new JButton();
			this.add(buttons[i]);
			buttons[i].setEnabled(false);
			// buttons[i].setVisible(false);
			buttons[i].setBackground(Color.YELLOW);
			buttons[i].setBorderPainted(false);
			if (i == 5) {
				playAgain = new JButton("Play Again");
				playAgain.setBackground(Color.CYAN);
				this.add(playAgain);
				playAgain.setOpaque(true);
				playAgain.setEnabled(true);
			}
			if (i == 6) {
				quit = new JButton("Quit");
				quit.setBackground(Color.CYAN);
				this.add(quit);
				quit.setOpaque(true);
				quit.setEnabled(true);
			}

		}
		Handler handler = new Handler(this.game);
		playAgain.addActionListener(handler);
		quit.addActionListener(handler);
	}

	private class Handler implements ActionListener {
		private Game game;

		public Handler(Game game) {
			this.game = game;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == playAgain) {
				Game g = new Game();
				this.game.w.setVisible(false);
			} else if (event.getSource() == quit)
				System.exit(0);
		}
	}
}
