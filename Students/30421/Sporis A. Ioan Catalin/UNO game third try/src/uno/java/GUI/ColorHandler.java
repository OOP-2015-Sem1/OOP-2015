package uno.java.GUI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import uno.java.game.Game;

public class ColorHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		if (((JPanel) e.getSource()).getBackground().equals(Color.RED)) {
			Game.gameWindow.gameColor = Color.RED;
		} else if (((JPanel) e.getSource()).getBackground().equals(Color.BLUE)) {
			Game.gameWindow.gameColor = Color.BLUE;
		} else if (((JPanel) e.getSource()).getBackground().equals(Color.YELLOW)) {
			Game.gameWindow.gameColor = Color.YELLOW;
		} else if (((JPanel) e.getSource()).getBackground().equals(Color.GREEN)) {
			Game.gameWindow.gameColor = Color.GREEN;

		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
