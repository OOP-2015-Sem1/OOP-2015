package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JButton ctrlButton[][];

	public ControlsPanel() {

		setLayout(new GridLayout(3, 3));
		ctrlButton = new JButton[3][3];

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				ctrlButton[i][j] = new JButton();
				ctrlButton[i][j].setBorder(Constants.LINE_BORDER);
				ctrlButton[i][j].setSize(new Dimension(700, 100));
				add(ctrlButton[i][j]);
			}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (i % 2 == 0 && j % 2 == 0) {
					ctrlButton[i][j].setEnabled(false);
					ctrlButton[i][j].setBackground(Color.WHITE);
				} else
					ctrlButton[i][j].setBackground(Color.LIGHT_GRAY);

			}
		}

		ctrlButton[0][1].setText(Constants.UP);
		ctrlButton[1][0].setText(Constants.LEFT);
		ctrlButton[1][2].setText(Constants.RIGHT);
		ctrlButton[2][1].setText(Constants.DOWN);
		ctrlButton[1][1].setBackground(Color.white);
		ctrlButton[1][1].setEnabled(false);
	}

	public JButton[][] getCtrlButton() {
		return this.ctrlButton;
	}

	public void addActionListenerToButtons(ActionListener actionListener) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ctrlButton[i][j].addActionListener(actionListener);

			}
		}
	}

}
