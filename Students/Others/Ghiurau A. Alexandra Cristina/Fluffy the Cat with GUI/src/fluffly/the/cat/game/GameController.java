package fluffly.the.cat.game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.security.Key;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import fluffly.the.cat.game.views.GamePanel;
import fluffy.the.cat.io.AbstractGameBoard;
import fluffy.the.cat.io.BoardConsolePrinter;
import fluffy.the.cat.io.FluffySavegameManager;
import fluffy.the.cat.io.FluffyTheCatFileReader;
import fluffy.the.cat.models.Fluffy;

public class GameController implements KeyListener {

	private final BoardConfiguration boardConfiguration;
	private final FluffySavegameManager savegameManager;

	private final Fluffy fluffy;
	private final GamePanel printer;


	public GameController(GamePanel printer) {

		this.fluffy = new Fluffy();
		this.boardConfiguration = new BoardConfiguration(this.fluffy);

		this.printer = printer;
		this.printer.getFrame().addKeyListener(this);

		FluffyTheCatFileReader fileReader = new FluffyTheCatFileReader(Constants.CONFIG_FILE_NAME);
		fileReader.readBoard(boardConfiguration.getBoard());

		try {
			fluffy.setPosition(boardConfiguration.getInitialFluffyPosition());
		} catch (FluffyNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		this.savegameManager = new FluffySavegameManager(Constants.FLUFFY_HAT_DAT);

		this.run(new String());
	}

	public void keyPressed(KeyEvent e) {
		this.run(Character.toString(e.getKeyChar()));

	}

	public void run(String direction) {

		if (direction.equals(Constants.UP) || direction.equals(Constants.DOWN) || direction.equals(Constants.LEFT)
				|| direction.equals(Constants.RIGHT)) {
			Point newFluffyPosition = getNewFluffyPosition(direction);
			if (boardConfiguration.isValidNewPosition(newFluffyPosition)) {
				if (boardConfiguration.fluffyReachedTheHat(newFluffyPosition)) {
					System.out.println("Miao, I bet I got my hat.");
					endGame();
				}
				boardConfiguration.updateFluffyOnBoard(fluffy.getPosition(), newFluffyPosition);
				fluffy.setPosition(newFluffyPosition);
			}
		} else if (direction.equals(Constants.SAVE)) {
			saveGame();
		} else if (direction.equals(Constants.RESTORE)) {
			restoreGame();
		} else if (direction.equals(Constants.QUIT)) {
			endGame();
		} else {
			System.out.println("Invalid command.");
		}

		boardConfiguration.printBoard(this.printer);
	}

	private Point getNewFluffyPosition(String direction) {

		Point newFluffyPosition = new Point();

		switch (direction) {
		case Constants.UP:
			newFluffyPosition.setLocation(fluffy.getPosition().x - Constants.SPEED, fluffy.getPosition().y);
			break;
		case Constants.DOWN:
			newFluffyPosition.setLocation(fluffy.getPosition().x + Constants.SPEED, fluffy.getPosition().y);
			break;
		case Constants.LEFT:
			newFluffyPosition.setLocation(fluffy.getPosition().x, fluffy.getPosition().y - Constants.SPEED);
			break;
		case Constants.RIGHT:
			newFluffyPosition.setLocation(fluffy.getPosition().x, fluffy.getPosition().y + Constants.SPEED);
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
				boardConfiguration.updateFluffyOnBoard(oldFluffyPosition, fluffy.getPosition());
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Could not restore Fluffy save game!");
			}
		} else {
			System.out.println("Could not restore Fluffy save game!");
		}
	}

	private void endGame() {
		System.out.println("Ending game...");
		System.exit(0);
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
