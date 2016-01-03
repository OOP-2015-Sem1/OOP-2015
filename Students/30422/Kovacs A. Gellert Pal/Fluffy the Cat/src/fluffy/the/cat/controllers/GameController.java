package fluffy.the.cat.controllers;


import fluffy.the.cat.io.BoardWindowPrinter;
import fluffy.the.cat.io.FluffySavegameManager;
import fluffy.the.cat.io.FluffyTheCatFileReader;
import fluffy.the.cat.models.Fluffy;
import fluffy.the.cat.views.GameFrame;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import fluffly.the.cat.game.BoardConfiguration;
import fluffly.the.cat.game.FluffyNotFoundException;

public class GameController {

	private final BoardConfiguration boardConfiguration;
	private final GameFrame gameFrame;
	private final Fluffy fluffy;
	private final FluffySavegameManager savegameManager;

	private static final String CONFIG_FILE_NAME = "FluffyWorld.txt";
	private static final String FLUFFY_HAT_DAT = "FluffyHat.dat";

	private static final String RESTORE = "r";
	private static final String SAVE = "s";
	private static final String RIGHT = "k";
	private static final String LEFT = "h";
	private static final String DOWN = "j";
	private static final String UP = "u";
	private static final String QUIT = "q";
	private static final int SPEED = 1;

	public GameController() {
		this.fluffy = new Fluffy();
		this.boardConfiguration = new BoardConfiguration(this.fluffy);
		FluffyTheCatFileReader fileReader = new FluffyTheCatFileReader(CONFIG_FILE_NAME);
		fileReader.readBoard(boardConfiguration.getBoard());

		try {
			fluffy.setPosition(boardConfiguration.getInitialFluffyPosition());
		} catch (FluffyNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		this.savegameManager = new FluffySavegameManager(FLUFFY_HAT_DAT);
		
		gameFrame = new GameFrame("Fluffy the Cat", this.boardConfiguration);
		gameFrame.setDirectionKeyListener(new DirectionKeyListener());
		JOptionPane.showMessageDialog(gameFrame, "Help Fluffy find his hat. \nUse the arrow keys to move.\nS - Save position\nR - Restore postion\nQ - Quit game", "Welcome", JOptionPane.INFORMATION_MESSAGE);
	}

	private class DirectionKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			
			String direction = new String();
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP: direction = UP; break;
			case KeyEvent.VK_DOWN: direction = DOWN; break;
			case KeyEvent.VK_RIGHT: direction = RIGHT; break;
			case KeyEvent.VK_LEFT: direction = LEFT; break;
			case KeyEvent.VK_S: direction = SAVE; break;
			case KeyEvent.VK_R: direction = RESTORE; break;
			case KeyEvent.VK_Q: direction = QUIT; break;
			}
			
			if (direction.equals(UP) || direction.equals(DOWN) || direction.equals(LEFT)
					|| direction.equals(RIGHT)) {
				Point newFluffyPosition = getNewFluffyPosition(direction);
				if (boardConfiguration.isValidNewPosition(newFluffyPosition)) {
					if (boardConfiguration
							.fluffyReachedTheHat(newFluffyPosition)) {
						JOptionPane.showMessageDialog(gameFrame, "Miao, I bet I got my hat.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
						endGame();
					}
					boardConfiguration.updateFluffyOnBoard(
							fluffy.getPosition(), newFluffyPosition);
					fluffy.setPosition(newFluffyPosition);
				}
			} else if (direction.equals(SAVE)) {
				saveGame();
			} else if (direction.equals(RESTORE)) {
				restoreGame();
			} else if (direction.equals(QUIT)) {
				endGame();
			}
			
			boardConfiguration.printBoard(new BoardWindowPrinter(gameFrame.getPanelMatrix()));
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private Point getNewFluffyPosition(String direction) {
		Point newFluffyPosition = new Point();
		switch (direction) {
		case UP:
			newFluffyPosition.setLocation(fluffy.getPosition().x - SPEED, fluffy.getPosition().y);
			break;
		case DOWN:
			newFluffyPosition.setLocation(fluffy.getPosition().x + SPEED, fluffy.getPosition().y);
			break;
		case LEFT:
			newFluffyPosition.setLocation(fluffy.getPosition().x, fluffy.getPosition().y - SPEED);
			break;
		case RIGHT:
			newFluffyPosition.setLocation(fluffy.getPosition().x, fluffy.getPosition().y + SPEED);
			break;
		}
		return newFluffyPosition;
	}
	
	private void saveGame() {
		try {
			savegameManager.saveGame(fluffy.getPosition());
		} catch (IOException e) {
			System.out.println("Could not save Fluffy game!");
		}
	}

	private void restoreGame() {
		if (savegameManager.canRestoreGame()) {
			try {
				Point oldFluffyPosition = new Point(fluffy.getPosition());
				this.fluffy.setPosition(savegameManager.restoreGame());
				boardConfiguration.updateFluffyOnBoard(oldFluffyPosition,
						fluffy.getPosition());
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Could not restore Fluffy save game!");
			}
		} else {
			System.out.println("Could not restore Fluffy save game!");
		}
	}
	
	private void endGame() {
		System.exit(0);
	}
}
