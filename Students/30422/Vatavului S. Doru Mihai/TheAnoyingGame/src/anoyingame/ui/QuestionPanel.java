package anoyingame.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class QuestionPanel extends JPanel {
	private JTextArea question;
	private String[] questionArray = new String[] { "What is the color of the shape?", "What is the color of the text?",
			"What is the color that's written?" };
	private Random rand = new Random();
	private int questionNumber = rand.nextInt(3);

	public QuestionPanel() {

		question = new JTextArea();
		setQuestionNumber();
		question.setEditable(false);
		question.setBackground(new Color(251, 253, 163));
		this.setBackground(new Color(251, 253, 163));
		this.add(question);
	}

	public void setQuestionNumber() {
		questionNumber = rand.nextInt(3);
		question.setText(questionArray[questionNumber]);
		question.setForeground(new Color(1, 145, 156));
		question.setFont(question.getFont().deriveFont(Font.BOLD));

	}

	public int getQuestionNumber() {
		return questionNumber;
	}

}
