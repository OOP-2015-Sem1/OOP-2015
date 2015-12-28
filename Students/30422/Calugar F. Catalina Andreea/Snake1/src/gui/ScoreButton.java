package gui;

import java.awt.Color;

import javax.swing.JButton;

public class ScoreButton extends JButton {

	private static final long serialVersionUID = 1L;
	public JButton scr;

	public ScoreButton() {
		scr = new JButton("Score: 0");
		scr.setEnabled(false);
		scr.setBackground(Color.WHITE);
	}

	public void setCurrentScore(int score) {
		scr.setText(String.valueOf("Score: " + score));
		System.out.println(score);
	}
}
