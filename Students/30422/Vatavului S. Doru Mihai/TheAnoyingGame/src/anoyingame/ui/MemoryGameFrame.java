package anoyingame.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class MemoryGameFrame extends JFrame {

	private ShapeAnimation shapeAnimation = new ShapeAnimation();
	private Timer timer = null;
	private JLabel timerLabel = new JLabel();
	private int count;
	private AnswerPanel answerPanel;
	private int[] correctAnswer;
	private int noOfShape;
	private JButton[] answerButtons;
	private int length;
	private int score = 0;
	private int animationNumber = 0;
	private JLabel scoreLabel = new JLabel("Score: " + score);

	public MemoryGameFrame(int length) {
		this.length = length;
		super.setTitle("Shape Game");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(timerLabel, BorderLayout.PAGE_START);
		this.add(scoreLabel, BorderLayout.LINE_END);
		this.add(shapeAnimation, BorderLayout.CENTER);
		answerPanel = new AnswerPanel(length);
		this.add(answerPanel, BorderLayout.PAGE_END);
		this.setSize(400, 500);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.answerButtons = answerPanel.getAnswerButtons();
		answerPanel.setLength(length);
		startTimer();
		for (int i = 0; i < 6; i++) {
			answerButtons[i].addActionListener(new FrameListener());
		}
		correctAnswer = new int[length];
	}

	public boolean userIsRight() {
		if (answerPanel.getUserAnswer() == correctAnswer)
			return true;
		else
			return false;
	}

	public void updateScore() {
		if (userIsRight() == true) {
			score++;
			scoreLabel.setText("Score: " + score);
		}
	}

	public void checkIfTimeToStop() {
		if (animationNumber == 10) {
			this.dispose();
			JOptionPane.showMessageDialog(null, "Game Over. Your score is: " + score + " out of 10.");
		}
	}

	public void startTimer() {
		updateScore();
		count = length;
		noOfShape = 0;
		TimeClass tc = new TimeClass(count);
		timer = new Timer(500, tc);
		timer.start();
	}

	public void checkForStartingTimer() {
		if (answerPanel.itsTimeForNewAnimation() == true) {
			checkIfTimeToStop();
			startTimer();
		}
	}

	private class TimeClass implements ActionListener {
		int counter;

		public TimeClass(int counter) {
			this.counter = counter;
		}

		public void actionPerformed(ActionEvent tc) {
			counter--;
			int display = counter + 1;
			shapeAnimation.setRandomShape();
			correctAnswer[noOfShape] = shapeAnimation.getShape();
			noOfShape++;
			shapeAnimation.setRandomColor();
			shapeAnimation.repaint();
			if (counter >= 1) {
				timerLabel.setText("Shapes left: " + display);

			} else {

				animationNumber++;
				timer.stop();
				timerLabel.setText("END");

			}

		}
	}

	private class FrameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 6; i++)
				if (e.getSource() == answerButtons[i])
					;
			checkForStartingTimer();

		}

	}
}
