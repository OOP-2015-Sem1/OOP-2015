package io;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DimensionOfBoardFrame implements ChangeListener {

	public JFrame dFrame;
	public JSlider jSliderRows;
	public JSlider jSliderCols;
	private JLabel jLabelRows;
	private JLabel jLabelCols;
	public JButton okButton;
	public int DIMENSION_OF_BOARD_ROWS = 8;
	public int DIMENSION_OF_BOARD_COLS = 8;

	public DimensionOfBoardFrame() {
		dFrame = new JFrame("Othello");
		jLabelRows = new JLabel("How many ROWS?", JLabel.CENTER);
		jLabelCols = new JLabel("How many COLUMNS?", JLabel.CENTER);

		okButton = new JButton("OK");
		// okButton.addActionListener((ActionListener) this);

		jSliderRows = new JSlider(JSlider.HORIZONTAL, 4, 20, 8);
		jSliderRows.setMinorTickSpacing(2);
		jSliderRows.setMajorTickSpacing(4);
		jSliderRows.setPaintTicks(true);
		jSliderRows.setPaintLabels(true);
		jSliderRows.addChangeListener((ChangeListener) this);

		jSliderCols = new JSlider(JSlider.HORIZONTAL, 4, 20, 8);
		jSliderCols.setMinorTickSpacing(2);
		jSliderCols.setMajorTickSpacing(4);
		jSliderCols.setPaintTicks(true);
		jSliderCols.setPaintLabels(true);
		jSliderCols.addChangeListener((ChangeListener) this);

		dFrame.add(jLabelRows);
		dFrame.add(jSliderRows);
		dFrame.add(jLabelCols);
		dFrame.add(jSliderCols);
		dFrame.add(okButton);
		dFrame.setLayout(new GridLayout(5, 1));

		dFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dFrame.setSize(500, 400);
		dFrame.setLocationRelativeTo(null); 
		dFrame.setVisible(true);
	}

	public void stateChanged(ChangeEvent event) {
		if (event.getSource() == jSliderRows) {
			if (!jSliderRows.getValueIsAdjusting()) {
				DIMENSION_OF_BOARD_ROWS = (int) jSliderRows.getValue();
				System.out.println("" + DIMENSION_OF_BOARD_ROWS);
			}
		} else if (event.getSource() == jSliderCols) {
			if (!jSliderRows.getValueIsAdjusting()) {
				DIMENSION_OF_BOARD_COLS = (int) jSliderCols.getValue();
				System.out.println("" + DIMENSION_OF_BOARD_COLS);
			}
		}
	}
}
