package cat.the.fluffeh.game;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import cat.the.fluffeh.io.Loader;
import cat.the.fluffeh.io.Saver;
import cat.the.fluffeh.models.Fluffeh;

/**
 * 
 * @author alexh Contains a matrix representing the level and other variables in
 *         regards to it This class represents the model and has some controller
 *         features: IO and game some game logic.
 */
public class GameState implements Saver, Loader {
	public static final String SAVE_PATH = "res/save.dat";
	private static final String WORLD_PATH = "res/world.txt";

	private int rows;
	private int cols;
	private int viewDistance;
	private CellContent[][] cellContents;
	private Fluffeh fluffeh;

	private boolean displayInstructions;
	private boolean gameOver;

	public GameState() {

		this.gameOver = false;

		if (!this.load(SAVE_PATH)) {
			this.loadWorld();
		}

		this.save(SAVE_PATH);
	}

	// Modifies Fluffeh's position sets gameOver if Fluffeh reached the
	// destination
	public void move(char c) {
		Point oldPosition = this.fluffeh.getPosition();

		// Get the new position
		int newX = oldPosition.x;
		int newY = oldPosition.y;
		switch (c) {
		case 'w':
			newX--;
			break;
		case 'a':
			newY--;
			break;
		case 's':
			newX++;
			break;
		case 'd':
			newY++;
			break;
		}

		// Check if in bounds
		if (newX < 0) {
			newX = 0;
		} else if (newX >= this.rows) {
			newX = this.rows - 1;
		}

		if (newY < 0) {
			newY = 0;
		} else if (newY >= this.cols) {
			newY = this.cols - 1;
		}

		// Check the cell you are about to enter
		switch (this.cellContents[newX][newY]) {
		case EMPTY:
		case HATWALL:
			break;
		case HAT:
			this.gameOver = true;
			break;
		// Revert to the old position
		default:
			newX = oldPosition.x;
			newY = oldPosition.y;
			break;
		}

		Point newPosition = new Point(newX, newY);
		this.fluffeh.setPosition(newPosition);
	}

	// Save and load for the current game state
	@Override
	public boolean save(String path) {

		try {
			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this.rows);
			oos.writeObject(this.cols);
			oos.writeObject(this.cellContents);
			oos.writeObject(this.fluffeh.getPosition());
			oos.writeObject(this.fluffeh.getViewDistance());
			oos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean load(String path) {
		try {
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.rows = (Integer) ois.readObject();
			this.cols = (Integer) ois.readObject();
			this.cellContents = (CellContent[][]) ois.readObject();
			Point pos = (Point) ois.readObject();
			int viewDist = (Integer) ois.readObject();
			this.fluffeh = new Fluffeh(pos, viewDist);
			ois.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Methods that are used by the controller
	public void newGame() {
		this.setDisplayInstructions(true);
		this.loadWorld();
		this.quickSave();
	}

	public void quickSave() {
		this.save(SAVE_PATH);
	}

	public void quickLoad() {
		this.load(SAVE_PATH);
	}

	// Additional load in case the save file does not exist
	private boolean loadWorld() {
		try {
			Scanner input = new Scanner(new File(WORLD_PATH));

			// First line contains rows cols viewDist
			String[] firstLine = input.nextLine().split(" ");
			this.rows = Integer.parseInt(firstLine[0]);
			this.cols = Integer.parseInt(firstLine[1]);
			this.viewDistance = Integer.parseInt(firstLine[2]);

			this.cellContents = new CellContent[this.rows][this.cols];
			Point initalFluffehPosition = null;
			// Next rows lines contains cols characters each
			for (int row = 0; row < this.rows; row++) {
				String line = input.nextLine();
				if (line.length() < this.cols) {
					throw new Exception("You messed up the world file!");
				}

				for (int col = 0; col < this.cols; col++) {
					switch (line.charAt(col)) {
					case '*':
						this.cellContents[row][col] = CellContent.WALL;
						break;
					case 'H':
						this.cellContents[row][col] = CellContent.HATWALL;
						break;
					case 'W':
						this.cellContents[row][col] = CellContent.HAT;
						break;
					case 'F':
						initalFluffehPosition = new Point(row, col);
					case ' ':
					default:
						this.cellContents[row][col] = CellContent.EMPTY;
						break;
					}
				}
			}

			if (initalFluffehPosition == null) {
				throw new Exception("Have you seen this Fluffeh?");
			}
			this.fluffeh = new Fluffeh(initalFluffehPosition, this.viewDistance);

			input.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Fluffeh getFluffeh() {
		return fluffeh;
	}

	public CellContent[][] getCellContents() {
		return this.cellContents;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isDisplayInstructions() {
		return displayInstructions;
	}

	public void setDisplayInstructions(boolean displayInstructions) {
		this.displayInstructions = displayInstructions;
	}
}
