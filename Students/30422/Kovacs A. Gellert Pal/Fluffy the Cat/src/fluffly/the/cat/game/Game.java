package fluffly.the.cat.game;

import java.awt.Point;
import java.io.IOException;
import java.util.Scanner;

import fluffy.the.cat.io.BoardConsolePrinter;
import fluffy.the.cat.io.FluffySavegameManager;
import fluffy.the.cat.io.FluffyTheCatFileReader;
import fluffy.the.cat.models.Fluffy;

public class Game {
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

	private final BoardConfiguration boardConfiguration;
	private final FluffySavegameManager savegameManager;

	private final Fluffy fluffy;

	public Game() {
		this.fluffy = new Fluffy();
		this.boardConfiguration = new BoardConfiguration(this.fluffy);
		FluffyTheCatFileReader fileReader = new FluffyTheCatFileReader(
				CONFIG_FILE_NAME);
		fileReader.readBoard(boardConfiguration.getBoard());

		try {
			fluffy.setPosition(boardConfiguration.getInitialFluffyPosition());
		} catch (FluffyNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		this.savegameManager = new FluffySavegameManager(FLUFFY_HAT_DAT);
	}

	public void run() {
		System.out
				.println("Keys: u - UP \t j - DOWN \t h - LEFT \t k - RIGHT\n\n\n Good luck! \n\n\n");

		Scanner s = new Scanner(System.in);
		boolean run = true;
		while (run) {
			boardConfiguration.printBoard(new BoardConsolePrinter());
			String direction = s.nextLine();
			if (direction.equals(UP) || direction.equals(DOWN) || direction.equals(LEFT)
					|| direction.equals(RIGHT)) {
				Point newFluffyPosition = getNewFluffyPosition(direction);
				if (boardConfiguration.isValidNewPosition(newFluffyPosition)) {
					if (boardConfiguration
							.fluffyReachedTheHat(newFluffyPosition)) {
						System.out.println("Miao, I bet I got my hat.");
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
				s.close();
				endGame();
			} else {
				System.out.println("Invalid command.");
			}
		}

	}

	private Point getNewFluffyPosition(String direction) {
		Point newFluffyPosition = new Point();
		switch (direction) {
		case UP:
			newFluffyPosition.setLocation(fluffy.getPosition().x - SPEED,
					fluffy.getPosition().y);
			break;
		case DOWN:
			newFluffyPosition.setLocation(fluffy.getPosition().x + SPEED,
					fluffy.getPosition().y);
			break;
		case LEFT:
			newFluffyPosition.setLocation(fluffy.getPosition().x,
					fluffy.getPosition().y - SPEED);
			break;
		case RIGHT:
			newFluffyPosition.setLocation(fluffy.getPosition().x,
					fluffy.getPosition().y + SPEED);
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
		System.out.println("Ending game...");
		System.exit(0);
	}
}
