package mainControllerPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import model.SoundPlayer;
import userInterfacePack.PuzzleFrame;

public class PuzzleController {

	private String pathImage = "resources/Image.png"; // default
	private String difficultyLevel = "easy"; // default
	private PuzzleFrame puzzleFrame;

	public void runPuzzle() {

		puzzleFrame = new PuzzleFrame(pathImage, difficultyLevel);
		puzzleFrame.addActionListenerToButtons(new RightPanelButtonsActionListener());
		puzzleFrame.addActionListenerToImageSegments(new ClickAction());
		puzzleFrame.addExitActionListenerToMenuButton(new ExitAction());
		puzzleFrame.addChangeImageActionListenertoMenuButton(new ChangeImageAction());
		puzzleFrame.addActionListenerToDifficultyMenuButton(new DifficultyActionListener());

	}

	private class DifficultyActionListener implements ActionListener {
		JMenuItem[] difficultyOp = puzzleFrame.getDifficultyOptions();

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == difficultyOp[0]) {
				difficultyLevel = "easy";
				newGame();

			}
			if (event.getSource() == difficultyOp[1]) {
				difficultyLevel = "hard";
				newGame();
			}
		}
	}

	private class ExitAction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	private class ChangeImageAction implements ActionListener {

		JMenuItem[] images = puzzleFrame.getImageOptions();

		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == images[0]) {
				pathImage = "resources/flower.png";
				newGame();

			}
			if (event.getSource() == images[1]) {
				pathImage = "resources/diCaprio.png";
				newGame();

			}
			if (event.getSource() == images[2]) {
				pathImage = "resources/eiffel.png";
				newGame();

			}
			if (event.getSource() == images[3]) {
				pathImage = "resources/singapore.png";
				newGame();

			}

		}
	}

	private class ClickAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			puzzleFrame.checkButtonAndSolution(e);
		}
	}

	private class RightPanelButtonsActionListener implements ActionListener {

		JButton newGameButton = puzzleFrame.getNewGameButton();
		JButton musicButton = puzzleFrame.getMusicButton();
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			File music = new File("beeth5th.wav");
			
			
			if (e.getSource() == newGameButton) {
				newGame();
			}
			if (e.getSource() == musicButton) {
				SoundPlayer.playSound(music,true);
			}
			
			
		}

	}

	private void newGame() {

		puzzleFrame.setVisible(false);
		puzzleFrame.dispose();

		runPuzzle();
	}
}
