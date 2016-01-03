package userInterfacePack;

import mainControllerPack.Constants;
import java.awt.event.*;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ButtonsPanel extends JPanel {

	private static final long serialVersionUID = -7551276221567061256L;

	private JButton newGameButton;
	private JButton countingButton;
	private JButton musicButton;

	public ButtonsPanel() {

		newGameButton = new JButton("NEW GAME");
		countingButton = new JButton("CLICKS");
		musicButton = new JButton("MUSIC");

		musicButton.setBorder(Constants.CONTROL_BUTTONS_LINE_BORDER);
		newGameButton.setBorder(Constants.CONTROL_BUTTONS_LINE_BORDER);
		countingButton.setBorder(Constants.CONTROL_BUTTONS_LINE_BORDER);

		add(newGameButton);
		add(countingButton);
		add(musicButton);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	}

	public void setNrOfClicks(int nrOfClicks) {
		countingButton.setText(String.valueOf(nrOfClicks));
	}

	public JButton getNewGameButton() {
		return this.newGameButton;

	}

	public JButton getMusicButton() {
		return this.musicButton;
	}

	public void addActionListenerToButtons(ActionListener actionListener) {

		newGameButton.addActionListener(actionListener);
		musicButton.addActionListener(actionListener);

	}

}
