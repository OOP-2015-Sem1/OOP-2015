package fluffly.the.cat.game;

import java.awt.Point;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import fluffy.the.cat.io.BoardConsolePrinter;
import fluffy.the.cat.io.BoardFrame;
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
	
	public void executeCommand(String command) {
//		boardConfiguration.printBoard(new BoardConsolePrinter());
		if (command.equals(UP) || command.equals(DOWN) || command.equals(LEFT)
				|| command.equals(RIGHT)) {
			Point newFluffyPosition = getNewFluffyPosition(command);
			if (boardConfiguration.isValidNewPosition(newFluffyPosition)) {
				if (boardConfiguration
						.fluffyReachedTheHat(newFluffyPosition)) {
					System.out.println("Miao, I bet I got my hat.");
					endGame();
				}
//				boardConfiguration.updateFluffyOnBoard(
//						fluffy.getPosition(), newFluffyPosition);
				fluffy.setPosition(newFluffyPosition);
			}
		} else if (command.equals(SAVE)) {
			saveGame();
		} else if (command.equals(RESTORE)) {
			restoreGame();
		} else if (command.equals(QUIT)) {
			endGame();
		} else {
			System.out.println("Invalid command.");
		}
		
	}

	public void runConsole() {
		System.out
				.println("Keys: u - UP \t j - DOWN \t h - LEFT \t k - RIGHT\n\n\n Good luck! \n\n\n");

		Scanner s = new Scanner(System.in);
		boolean run = true;
		while (run) {
			String command = s.nextLine();
			executeCommand(command);
		}

	}
	
	public void runFrame() {
		BoardFrame frame = new BoardFrame(this);// it is the first time when the board is printed so we want to see the content
		frame.myFrame.setVisible(true);
		frame.myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.myFrame.setSize(1600, 800);
	}
	
	
	private static final int[] DISTX = {-1, 1, 0, 0};
	private static final int[] DISTY = {0, 0, -1, 1};
	
	private Point getNewFluffyPosition(String command) {
		Point newFluffyPosition = new Point();
		switch (command) {
		case UP:
			newFluffyPosition.setLocation(fluffy.getPosition().x + DISTX[0],
					fluffy.getPosition().y + DISTY[0]);
			break;
		case DOWN:
			newFluffyPosition.setLocation(fluffy.getPosition().x + DISTX[1],
					fluffy.getPosition().y + DISTY[1]);
			break;
		case LEFT:
			newFluffyPosition.setLocation(fluffy.getPosition().x + DISTX[2],
					fluffy.getPosition().y + DISTY[2]);
			break;
		case RIGHT:
			newFluffyPosition.setLocation(fluffy.getPosition().x + DISTX[3],
					fluffy.getPosition().y + DISTY[3]);
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

	public BoardConfiguration getBoardConfiguration() {
		return boardConfiguration;
	}

	public Fluffy getFluffy() {
		return fluffy;
	}
}
