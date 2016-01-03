package anoyingame.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AnswerPanel extends JPanel {

	private JButton[] answerButtons;
	private int[] userAnswer;
	private int noOfShape = 0;
	private boolean timeForNewAnimation = false;
	private int length;

	public AnswerPanel(int length) {
		setLayout(new GridLayout(2, 3));
		userAnswer = new int[length];
		answerButtons = new JButton[6];
		for (int i = 0; i < 6; i++) {
			Icon icon = new ShapeIcon(i + 1);
			answerButtons[i] = new JButton();
			answerButtons[i].setPreferredSize(new Dimension(60, 60));
			answerButtons[i].setBackground(Color.WHITE);
			answerButtons[i].setIcon(icon);
			answerButtons[i].addActionListener(new AnswerListener());
			answerButtons[i].setBackground(new Color(255, 202, 0));
			this.add(answerButtons[i]);
		}
	}

	public void setLength(int value) {
		length = value;
	}

	private class AnswerListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (noOfShape != length) {

				for (int i = 0; i < 6; i++) {
					if (event.getSource() == answerButtons[i]) {
						userAnswer[noOfShape] = i + 1;
						noOfShape++;
					}
				}
				if (noOfShape == length - 1) {
					timeForNewAnimation = true;

				}
				if (noOfShape == length) {
					noOfShape = 0;
					timeForNewAnimation = false;
				}
			}
		}
	}

	public boolean itsTimeForNewAnimation() {
		return timeForNewAnimation;
	}

	public int[] getUserAnswer() {
		return userAnswer;
	}

	public JButton[] getAnswerButtons() {
		return answerButtons;
	}
}
