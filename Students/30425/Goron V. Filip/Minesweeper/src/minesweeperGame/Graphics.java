package minesweeperGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import minesweeperHelp.Help;

@SuppressWarnings("serial")
public class Graphics extends JFrame implements MouseListener {
	public static Tiles[][] field = new Tiles[GamePlay.FIELD_LENGTH][GamePlay.FIELD_DEPTH];
	public static JButton[][] tileButton = new JButton[GamePlay.FIELD_LENGTH][GamePlay.FIELD_DEPTH];
	public static JButton help;
	public static JLabel bombCounter;
	public static JFrame gameWindow;
	public static JPanel buttonField;
	public static JPanel topBar;

	Graphics() {

		gameWindow = new JFrame("Minesweeper");
		buttonField = new JPanel();
		help = new JButton("Help");
		bombCounter = new JLabel("99");
		topBar = new JPanel();

		for (int j = 0; j < GamePlay.FIELD_DEPTH; j++) {
			for (int i = 0; i < GamePlay.FIELD_LENGTH; i++) {
				Graphics.field[i][j] = new Tiles(i, j);
				tileButton[i][j] = new JButton(Graphics.field[i][j].output());
				tileButton[i][j].setFont(new Font("Arial", Font.BOLD, 14));
				tileButton[i][j].addMouseListener(this);
				tileButton[i][j].setMargin(new Insets(0, 0, 0, 0));
				buttonField.add(tileButton[i][j]);
			}
		}

		help.addMouseListener(this);

		topBar.setLayout(new GridLayout());
		topBar.add(help);
		topBar.add(bombCounter);

		buttonField.setLayout(new GridLayout(GamePlay.FIELD_DEPTH, GamePlay.FIELD_LENGTH));
		buttonField.setVisible(true);

		gameWindow.setLayout(new BorderLayout());
		gameWindow.setSize(800, 440);
		gameWindow.setVisible(true);

		gameWindow.add(buttonField, BorderLayout.CENTER);
		gameWindow.add(topBar, BorderLayout.PAGE_START);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void refreshBombCounter(int bombs) {
		bombCounter.setText(Integer.toString(bombs));
	}

	public static void instructionsPopUp() {
		JOptionPane.showMessageDialog(null, "Click to open, RightClick to flag, and BOTH to open multiple",
				"Instructions", JOptionPane.PLAIN_MESSAGE);
	}

	public static void endGamePopUp(boolean won) {
		if (won) {
			JOptionPane.showMessageDialog(null, "You Won! :)", "Game Over", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "You Lost :(", "Game Over", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void mouseClicked(MouseEvent Click) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent Click) {

	}

	@Override
	public void mouseReleased(MouseEvent Click) {

		if (Click.getSource() == help) {
			Help.nextHelp();
		}

		for (int j = 0; j < GamePlay.FIELD_DEPTH; j++) {
			for (int i = 0; i < GamePlay.FIELD_LENGTH; i++) {
				if (Click.getSource() == tileButton[i][j]) {

					if (Click.getButton() == 3) {

						field[i][j].flag();

						
						break;

					} else if (Click.isShiftDown()) {
						field[i][j].openAround(i, j);

					} else {
						field[i][j].open();

					}
					Auxiliary.checkForWin();
				}
			}
		}

		if (SwingUtilities.isLeftMouseButton(Click) && SwingUtilities.isRightMouseButton(Click)) {

			for (int j = 0; j < GamePlay.FIELD_DEPTH; j++) {
				for (int i = 0; i < GamePlay.FIELD_LENGTH; i++) {
					if (Click.getSource() == tileButton[i][j]) {
						field[i][j].openAround(i, j);
					}
					Auxiliary.checkForWin();
				}
			}
		}
	}

}