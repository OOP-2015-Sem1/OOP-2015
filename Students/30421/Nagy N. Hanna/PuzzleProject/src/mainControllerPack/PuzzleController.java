package mainControllerPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import model.SoundPlayer;
import userInterfacePack.PuzzleFrame;

public class PuzzleController {

	private PuzzleFrame puzzleFrame;

	public void runPuzzle(String pathImage) {

		puzzleFrame = new PuzzleFrame(pathImage);
		puzzleFrame.addActionListenerToButtons(new RightPanelButtonsActionListener());
		puzzleFrame.addActionListenerToImageSegments(new ClickAction());
		puzzleFrame.addExitActionListenerToMenuButton(new ExitAction());
		puzzleFrame.addChangeImageActionListenertoMenuButton(new ChangeImageAction());
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
				newGame("resources/flower.png");

			}
			if (event.getSource() == images[1]) {
				newGame("resources/diCaprio.png");
			}
			if (event.getSource() == images[2]) {
				newGame("resources/eiffel.png");
			}
			if (event.getSource() == images[3]) {
				newGame("resources/singapore.png");
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
			if (e.getSource() == newGameButton) {
				newGame("resources/Image.png");
			}
			if (e.getSource() == musicButton) {

				File music = new File("beeth5th.wav");
				SoundPlayer.PlaySound(music);
			}
		}

	}
	
	private void newGame(String pathImage) {

		puzzleFrame.setVisible(false);
		puzzleFrame.dispose();

		runPuzzle(pathImage);
	}
}
