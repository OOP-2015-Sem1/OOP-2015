package fluffly.the.cat.game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import fluffy.the.cat.io.FluffySavegameManager;
import fluffy.the.cat.io.FluffyTheCatFileReader;
import fluffy.the.cat.io.GraphicBoardPrinter;
import fluffy.the.cat.models.Fluffy;

public class Game implements KeyListener {
	private static final String CONFIG_FILE_NAME = "FluffyWorld.txt";
	private static final String FLUFFY_HAT_DAT = "FluffyHat.dat";

	private static final String RESTORE = "r";
	private static final String SAVE = "s";
	private static final String RIGHT = "k";
	private static final String LEFT = "h";
	private static final String DOWN = "j";
	private static final String UP = "u";
	private static final String QUIT = "q";
	private static final int speed = 1;

	private final BoardConfiguration boardConfiguration;
	private final FluffySavegameManager savegameManager;
	private final GraphicBoardPrinter printer;

	private final Fluffy fluffy;

	public Game(GraphicBoardPrinter printer) {
		this.fluffy = new Fluffy();
		this.boardConfiguration = new BoardConfiguration(this.fluffy);
		
		// get printer from user, add action listener to frame
		this.printer = printer;
		this.printer.getFrame().addKeyListener(this);
		
		
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
		
		this.printStartMessage();
		
		// dummy run just to display the first screen
		this.run(new String());
	}
	
	public void keyPressed(KeyEvent ke) {
		this.run(Character.toString(ke.getKeyChar()));
			
	}
	
	
	public void printStartMessage() {
		System.out
		.println("Keys: u - UP \t j - DOWN \t h - LEFT \t k - RIGHT\n\n\n Good luck! \n\n\n");	
	}

	public void run(String direction) {

			
			
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
				endGame();
			} else {
				System.out.println("Invalid command.");
			}
			
			boardConfiguration.printBoard(this.printer);

	}

	private Point getNewFluffyPosition(String direction) {
		Point newFluffyPosition = new Point();
		switch (direction) {
		case UP:
			newFluffyPosition.setLocation(fluffy.getPosition().x - speed,
					fluffy.getPosition().y);
			break;
		case DOWN:
			newFluffyPosition.setLocation(fluffy.getPosition().x + speed,
					fluffy.getPosition().y);
			break;
		case LEFT:
			newFluffyPosition.setLocation(fluffy.getPosition().x,
					fluffy.getPosition().y - speed);
			break;
		case RIGHT:
			newFluffyPosition.setLocation(fluffy.getPosition().x,
					fluffy.getPosition().y + speed);
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

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
