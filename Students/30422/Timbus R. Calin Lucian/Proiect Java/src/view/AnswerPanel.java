package view;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Answerable;

public class AnswerPanel extends JPanel {
	private final int SQUARE_SIZE = 30;
	private final Answerable frame;
	private int row;
	private int column;

	public AnswerPanel(Answerable frame, int row, int column) {
		this.frame = frame;
		this.row = row;
		this.column = column;
		int count = 0;
		setLayout(new GridLayout(3, 3));
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				count++;
				JButton jButton = new JButton(String.valueOf(count));
				jButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.setAnswer(Integer.valueOf(jButton.getText()), row, column);
						removeAll();
						setLayout(new GridLayout(1, 1));
						SudokuButton sudokuButton = new SudokuButton(jButton.getText(), row, column);
						sudokuButton.setText(jButton.getText());
					}
				});
				add(jButton);
			
			}
		setSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
	}

	public int returnLine() {
		return this.row;
	}

	public int returnColumn() {
		return this.column;
	}
}
