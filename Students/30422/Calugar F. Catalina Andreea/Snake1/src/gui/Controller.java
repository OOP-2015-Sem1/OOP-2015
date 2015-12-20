package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

public class Controller implements KeyListener {

	private Frame snakeFrame;

	private ControlsPanel panel = new ControlsPanel();

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT && !Constants.left) {
			Constants.up = false;
			Constants.down = false;
			Constants.right = true;
		} else if (key == KeyEvent.VK_LEFT && !Constants.right) {
			Constants.up = false;
			Constants.down = false;
			Constants.left = true;

		} else if (key == KeyEvent.VK_UP && !Constants.down) {
			Constants.left = false;
			Constants.right = false;
			Constants.up = true;
		} else if ((key == KeyEvent.VK_DOWN) && (!Constants.up)) {
			Constants.left = false;
			Constants.right = false;
			Constants.down = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unused")
	private class SnakeMouseControllsClickListener implements ActionListener {

		private JButton[][] controlButtons;

	
		public SnakeMouseControllsClickListener() {
			// point to the same JButton matrix -> almost no additional memory
			this.setControlButtons(snakeFrame.getControlButtons());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == panel.ctrlButton[0][1] && !Constants.down) {
				Constants.left = false;
				Constants.right = false;
				Constants.up = true;
			}
			if (e.getSource() == panel.ctrlButton[1][2] && !Constants.left) {
				Constants.up = false;
				Constants.down = false;
				Constants.right = true;
			}
			if (e.getSource() == panel.ctrlButton[1][0] && !Constants.right) {
				Constants.up = false;
				Constants.down = false;
				Constants.left = true;
			}
			if (e.getSource() == panel.ctrlButton[2][1] && !Constants.up) {
				Constants.left = false;
				Constants.right = false;
				Constants.down = true;
			}

		}

		public JButton[][] getControlButtons() {
			return controlButtons;
		}

		public void setControlButtons(JButton[][] controlButtons) {
			this.controlButtons = controlButtons;
		}
	}
}
