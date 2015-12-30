package anoyingame.ui;
import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextArea;


public class QuestionPanel extends JPanel{
	private JTextArea question;
	private String[] questionArray = new String[]{
			"What is the color of the shape?",
			"What is the color of the text?",
			"What is the color that's written?"
	};
	private Random rand = new Random();
	private int questionNumber = rand.nextInt(3);
	public QuestionPanel(){
		
		question = new JTextArea();
		setQuestionNumber();
		question.setEditable(false);
		question.setBackground(Color.LIGHT_GRAY);
		this.setBackground(Color.LIGHT_GRAY);
		this.add(question);
	}
	
	public void setQuestionNumber(){
		questionNumber = rand.nextInt(3);
		question.setText(questionArray[questionNumber]);
		
	}
	public int getQuestionNumber(){
		return questionNumber;
	}
	
	public void printNumber() {
		System.out.println(questionNumber);
	}
	
	public void resetQuestion(){
		new QuestionPanel();
		
	}
}
