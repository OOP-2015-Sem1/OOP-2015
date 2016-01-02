package battleship.userInterface;

import static battleship.background.code.Constants.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.*;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton[][] buttons;

	public BoardPanel(int x, int y) {
		setLayout(new GridLayout(10, 10));

		buttons = new JButton[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setBackground(Color.LIGHT_GRAY);
				add(buttons[i][j]);
			}
		}
		this.setBounds(x, y, 250, 250);

	}

	public JButton[][] getButtons() {
		return this.buttons;
	}

	public void addActionListenerToButtons(MouseListener actionListener) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				buttons[i][j].addMouseListener(actionListener);
			}
		}
	}

	public void updateBoardWithShips(boolean[][] shipMatrix) {
		for (int i = 0; i < BUTTONS_PER_LINE; i++) {
			for (int j = 0; j < BUTTONS_PER_LINE; j++) {
				if (shipMatrix[i][j]) {
					buttons[i][j].setBackground(new Color(100, 30, 30));
				}
			}
		}

	}

	public void updateBoardWithHits(int i, int j) {
		if (buttons[i][j].getBackground() == new Color(100, 30, 30)) {
			buttons[i][j].setBackground(Color.RED);
		} else {
			buttons[i][j].setBackground(new Color(0, 100, 150));
		}
	}

	public JButton getSpecificButton(int i, int j) {
		return buttons[i][j];
	}

	public void EnableButtons(boolean isEnabled) {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				buttons[i][j].setEnabled(isEnabled);
			}
		}
	}

}
