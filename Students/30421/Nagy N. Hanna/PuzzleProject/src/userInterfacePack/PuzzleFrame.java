package userInterfacePack;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.*;

import mainControllerPack.Constants;
import model.SoundPlayer;

public class PuzzleFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private GamePanelP gamePanel;
	private ButtonsPanel panelWithButtons;

	// public static boolean SMALLER_OR_BIGGER_BOARDSIZE;
	public static boolean WITH_OR_WITHOUT_SOUND;

	private MenuBar menu;

	private int nrOfClicks = 0;

	public PuzzleFrame(String pathImage, String difficulty) {

		super("Puzzle");
		setLayout(new BorderLayout());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// SMALLER_OR_BIGGER_BOARDSIZE = QuariesFromUser.queryForBoardSize();
		WITH_OR_WITHOUT_SOUND = QuariesFromUser.queryForSound();

		File intro = new File("welcome.wav");
		SoundPlayer.playSound(intro, true);

		try {
			gamePanel = new GamePanelP(pathImage, difficulty);
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

		panelWithButtons = new ButtonsPanel();
		menu = new MenuBar();

		add(gamePanel, BorderLayout.WEST);
		add(new RightPanel(panelWithButtons), BorderLayout.EAST);
		setJMenuBar(menu);

		pack();
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void addActionListenerToDifficultyMenuButton(ActionListener actionListener) {
		menu.addActionListenerToDifficultyMenuButton(actionListener);
	}

	public void addExitActionListenerToMenuButton(ActionListener actionListener) {
		menu.addExitActionListenerToMenuButton(actionListener);

	}

	public void addChangeImageActionListenertoMenuButton(ActionListener actionListener) {
		menu.addChangeImagActionToMenuButton(actionListener);
	}

	public void addActionListenerToButtons(ActionListener actionListener) {
		panelWithButtons.addActionListenerToButtons(actionListener);
	}

	public void addActionListenerToImageSegments(ActionListener e) {
		gamePanel.addActionListenerToSegments(e);

	}

	public void checkButtonAndSolution(ActionEvent e) {
		gamePanel.checkButton(e);
		nrOfClicks++;
		panelWithButtons.setNrOfClicks(nrOfClicks);
		gamePanel.checkSolution();
	}

	public JButton getNewGameButton() {
		return this.panelWithButtons.getNewGameButton();
	}

	public JButton getMusicButton() {
		return this.panelWithButtons.getMusicButton();
	}

	public JMenuItem[] getImageOptions() {
		return this.menu.getImageOptions();
	}

	public JMenuItem[] getDifficultyOptions() {
		return this.menu.getDifficultyOptions();
	}

	public int getNrOfClicks() {
		return this.nrOfClicks;
	}

}
