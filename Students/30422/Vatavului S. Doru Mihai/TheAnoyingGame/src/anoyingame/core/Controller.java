package anoyingame.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import anoyingame.ui.GameChooser;

public class Controller {

	private DifficultyChooser difficultyChooser = new DifficultyChooser();
	private GameChooser chooser = new GameChooser();
	private JButton colorGameButton;
	private JButton memoryGameButton;
	private ImageIcon colorGameHeader = new ImageIcon(getClass().getResource("ColorGame.png"));
	private ImageIcon memoryGameHeader = new ImageIcon(getClass().getResource("MemoryGame.png"));

	public Controller() {

		colorGameButton = chooser.getButton1();
		memoryGameButton = chooser.getButton2();
		colorGameButton.addActionListener(new ChoiceListener());
		memoryGameButton.addActionListener(new ChoiceListener());
	}

	private class ChoiceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == colorGameButton)
				difficultyChooser.start(colorGameHeader, "color");
			else if (event.getSource() == memoryGameButton)
				difficultyChooser.start(memoryGameHeader, "memory");
			chooser.dispose();
		}
	}
}
