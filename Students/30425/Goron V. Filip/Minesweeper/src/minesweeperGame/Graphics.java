package minesweeperGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import minesweeperGame.Graphics;

@SuppressWarnings("serial")
public class Graphics extends JFrame implements MouseListener {
	static JButton[][] tileButton = new JButton[GamePlay.fieldLenght][GamePlay.fieldDepth];
	static JButton help;
	static JLabel bombCounter;
	static JFrame gameWindow;
	static JPanel buttonField;
	static JPanel topBar;

	Graphics() {

		gameWindow = new JFrame("Minesweeper");
		buttonField = new JPanel();
		help = new JButton();
		bombCounter = new JLabel("99");
		topBar = new JPanel();

		int i, j;

		for (j = 0; j < GamePlay.fieldDepth; j++) {
			for (i = 0; i < GamePlay.fieldLenght; i++) {
				tileButton[i][j] = new JButton(Main.field[i][j].output());
				tileButton[i][j].setFont(new Font("Arial", Font.BOLD, 14));
				buttonField.add(tileButton[i][j]);
				tileButton[i][j].addMouseListener(this);
			}
		}

		topBar.setLayout(new GridLayout());
		topBar.add(help);
		topBar.add(bombCounter);

		buttonField.setLayout(new GridLayout(GamePlay.fieldDepth, GamePlay.fieldLenght));
		buttonField.setVisible(true);

		gameWindow.setLayout(new BorderLayout());
		gameWindow.setSize(1280, 640);
		gameWindow.setVisible(true);

		gameWindow.add(buttonField, BorderLayout.CENTER);
		gameWindow.add(topBar, BorderLayout.PAGE_START);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void refreshBombCounter(int bombs) {
		bombCounter.setText(Integer.toString(bombs));
	}
	
	public static void instructionsPopUp(){
		JOptionPane.showMessageDialog(null, "Click to open, CTRL+Click to flag, and SHIFT+Click to open multiple", "Instructions",JOptionPane.PLAIN_MESSAGE);
	}

	public static void endGamePopUp(boolean won) {
		if (won == true) {
			JOptionPane.showMessageDialog(null, "You Won! :)", "Game Over", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "You Lost :(", "Game Over", JOptionPane.ERROR_MESSAGE);
		}
	}

	static int coordinates;

	public static int getCoordinates() {
		return coordinates;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent Click) {

		for (int j = 0; j < GamePlay.fieldDepth; j++) {
			for (int i = 0; i < GamePlay.fieldLenght; i++) {
				if (Click.getSource() == tileButton[i][j]) {

					if (Click.isControlDown() == true) {
						Main.field[i][j].flag(i, j);
						if (Main.field[i][j].flagged == true) {
							Graphics.tileButton[i][j].setBackground(Color.YELLOW);
							BombDistribution.flaggedBombs--;
							refreshBombCounter(BombDistribution.flaggedBombs);
						} else if (Main.field[i][j].opened == false) {
							Graphics.tileButton[i][j].setBackground(null);
							BombDistribution.flaggedBombs++;
							refreshBombCounter(BombDistribution.flaggedBombs);
						}
						break;
					} else if (Click.isShiftDown() == true) {
						Main.field[i][j].openAround(i, j);
					} else {
						Main.field[i][j].open(i, j);

					}
					coordinates = j * 15 + i;
					Auxiliary.checkForWin();
				}
			}
		}
	}
}