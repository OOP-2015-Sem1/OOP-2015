package project.memorygame.views;
import project.memorygame.controllers.Constants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import project.memorygame.controllers.Game;

@SuppressWarnings("serial")
public class DifficultyMenu extends JFrame {
	private JButton hard;
	private JButton medium;
	private JButton easy;
	private Game game;

	public DifficultyMenu(int width, int height, Game game) {
		this.game = game;
		this.setTitle("Difficulty Menu");
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setBackground(Color.black);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new GridLayout(7, 3));

		JButton buttons[] = new JButton[18];

		for (int i = 0; i < 18; i++) {
			buttons[i] = new JButton();
			this.add(buttons[i]);
			buttons[i].setEnabled(false);
			// buttons[i].setVisible(false);
			buttons[i].setBackground(Color.YELLOW);
			buttons[i].setBorderPainted(false);
			if (i == 3) {
				easy = new JButton("Easy");
				easy.setBackground(Color.CYAN);
				this.add(easy);
				easy.setOpaque(true);
				easy.setEnabled(true);
			}
			if (i == 8) {
				medium = new JButton("Medium");
				medium.setBackground(Color.CYAN);
				this.add(medium);
				medium.setOpaque(true);
				medium.setEnabled(true);
			}
			if (i == 13) {
				hard = new JButton("Hard");
				hard.setBackground(Color.CYAN);
				this.add(hard);
				hard.setOpaque(true);
				hard.setEnabled(true);
			}

		}	
		Handler handler=new Handler();
		easy.addActionListener(handler);
		medium.addActionListener(handler);
		hard.addActionListener(handler);
	}

	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == easy)
				game.enterGame(Constants.EASY);
			else if (event.getSource() == medium)
				game.enterGame(Constants.MEDIUM);
			else if (event.getSource()==hard)
				game.enterGame(Constants.HARD);
		}
	}
}


