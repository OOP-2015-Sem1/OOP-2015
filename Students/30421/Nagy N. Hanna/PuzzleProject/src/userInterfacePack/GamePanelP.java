package userInterfacePack;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mainControllerPack.Constants;
import model.ButtonSegments;
import model.ImageCreator;
import model.SoundPlayer;

public class GamePanelP extends JPanel {

	private static final long serialVersionUID = 1133604511959753736L;

	private ArrayList<ButtonSegments> buttons = new ArrayList<ButtonSegments>();
	private ArrayList solution = new ArrayList();

	private ButtonSegments lastButton = new ButtonSegments();

	public static int rows, cols, dimension;

	public GamePanelP(String pathImage, String difficulty) throws URISyntaxException {
		createGamePanel(pathImage, difficulty);
	}

	public void createGamePanel(String pathImage, String difficulty) throws URISyntaxException {

		/*
		 * if (!PuzzleFrame.SMALLER_OR_BIGGER_BOARDSIZE) { rows =
		 * Constants.DEFAULT1_ROW; cols = Constants.DEFAULT1_COLS; dimension =
		 * Constants.DEFAULT1_BOARD_SEGMENTS; } else { rows =
		 * Constants.DEFAULT2_ROW; cols = Constants.DEFAULT2_COLS; dimension =
		 * Constants.DEFAULT2_BOARD_SEGMENTS; }
		 */

		if (difficulty.equals("easy")) {
			rows = Constants.DEFAULT1_ROW;
			cols = Constants.DEFAULT1_COLS;
			dimension = Constants.DEFAULT1_BOARD_SEGMENTS;
		} else {

			rows = Constants.DEFAULT2_ROW;
			cols = Constants.DEFAULT2_COLS;
			dimension = Constants.DEFAULT2_BOARD_SEGMENTS;
		}

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				solution.add(new Point(i, j));
			}
		}

		setLayout(new GridLayout(rows, cols, 0, 0));
		setBorder(BorderFactory.createLineBorder(Color.RED));

		try {
			new ImageCreator(pathImage, buttons, lastButton);

		} catch (URISyntaxException e) {

			e.printStackTrace();
		}

		for (int i = 0; i < dimension - 1; i++) {

			ButtonSegments button = (ButtonSegments) buttons.get(i);
			add(button);
			button.setBorder(BorderFactory.createLineBorder(Color.gray));

		}

	}

	public ArrayList<ButtonSegments> getButtonSegments() {
		return this.buttons;
	}

	public void addActionListenerToSegments(ActionListener e) {
		for (int i = 0; i < dimension - 1; i++) {
			buttons.get(i).addActionListener((ActionListener) e);

		}

	}

	public void checkButton(ActionEvent e) {

		int lastButtonIndex = 0;
		for (ButtonSegments button : buttons) {
			if (button.isLastButton()) {
				lastButtonIndex = buttons.indexOf(button);
			}
		}

		JButton button = (JButton) e.getSource();
		int currentButtonIndex = buttons.indexOf(button);

		if ((currentButtonIndex - 1 == lastButtonIndex) || (currentButtonIndex + 1 == lastButtonIndex)
				|| (currentButtonIndex - rows == lastButtonIndex) || (currentButtonIndex + rows == lastButtonIndex)) {

			Collections.swap(buttons, currentButtonIndex, lastButtonIndex);
			updateButtons();

		} else {
			if (PuzzleFrame.WITH_OR_WITHOUT_SOUND)
				Toolkit.getDefaultToolkit().beep();
		}
	}

	private void updateButtons() {

		removeAll();
		for (JComponent button : buttons) {

			add(button);
		}
		validate();
	}

	public void checkSolution() {

		ArrayList currentListOfButtonSegments = new ArrayList();

		for (JComponent btn : buttons) {
			currentListOfButtonSegments.add((Point) btn.getClientProperty("position"));
		}

		if (compareList(solution, currentListOfButtonSegments)) {
			File clap = new File("applause.WAV");
			SoundPlayer.playSound(clap, true);
			String name = QuariesFromUser.queryForName();
			JOptionPane.showMessageDialog(this, "Congratulations dear " + name + "!", "Message",
					JOptionPane.INFORMATION_MESSAGE);

		}
	}

	public static boolean compareList(ArrayList list1, ArrayList list2) {
		return list1.toString().contentEquals(list2.toString());
	}

}
