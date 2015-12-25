package gui;

import static gui.Constants.DOWN;
import static gui.Constants.LEFT;
import static gui.Constants.RIGHT;
import static gui.Constants.UP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SnakeMouseControllsClickListener implements ActionListener {

	private Snake snake = new Snake();

	private ControlsPanel panel = new ControlsPanel();
	private JButton[][] ctrlButton;

	public SnakeMouseControllsClickListener() {
		// point to the same JButton matrix -> almost no additional memory
		this.setCtrlButton(getCtrlButton());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == panel.ctrlButton[0][1]) {
			snake.moveSnake(UP);
		}
		if (e.getSource() == panel.ctrlButton[1][2]) {
			snake.moveSnake(RIGHT);
		}
		if (e.getSource() == panel.ctrlButton[1][0]) {
			snake.moveSnake(LEFT);
		}
		if (e.getSource() == panel.ctrlButton[2][1]) {
			snake.moveSnake(DOWN);
		}

	}

	public JButton[][] getCtrlButton() {
		return ctrlButton;
	}

	public void setCtrlButton(JButton[][] ctrlButton) {
		this.ctrlButton = ctrlButton;
	}

}
