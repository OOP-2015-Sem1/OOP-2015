package anoyingame.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import anoyingame.ui.ColorGameFrame;
import anoyingame.ui.Menu;
import anoyingame.ui.MemoryGameFrame;

public class DifficultyChooser {

	private ColorGameFrame theFrame;
	private Menu theMenu;
	private JButton easyMode;
	private JButton mediumMode;
	private JButton hardMode;
	private ActionListener buttonAction;
	private int difficulty;

	public void start(ImageIcon header, String choice) {

		theMenu = new Menu(header);

		easyMode = theMenu.getButton("easy");
		mediumMode = theMenu.getButton("medium");
		hardMode = theMenu.getButton("hard");

		if (choice.equals("color"))
			buttonAction = new ColorMenuListener();
		else if (choice.equals("memory"))
			buttonAction = new MemoryMenuListener();

		easyMode.addActionListener(buttonAction);
		mediumMode.addActionListener(buttonAction);
		hardMode.addActionListener(buttonAction);
	}

	private class ColorMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == easyMode)
				difficulty = 1;
			else if (event.getSource() == mediumMode)
				difficulty = 2;
			else if (event.getSource() == hardMode)
				difficulty = 3;
			theMenu.dispose();
			theFrame = new ColorGameFrame();
			theFrame.setCount(difficulty);
			theFrame.allowTimerStart(true);
		}
	}

	private class MemoryMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == easyMode)
				difficulty = 4;
			else if (event.getSource() == mediumMode)
				difficulty = 6;
			else if (event.getSource() == hardMode)
				difficulty = 9;
			theMenu.dispose();
			new MemoryGameFrame(difficulty);
		}
	}
}
