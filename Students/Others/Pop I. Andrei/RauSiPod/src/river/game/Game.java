package river.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import river.io.BoardPrinter;
import river.io.ReadFromFile;
import river.models.Boat;
import river.models.Curl;
import river.models.Entity;
import river.models.Pole;

public class Game {

	private final String FILE_NAME = "river.txt";
	private final BoardConfiguration boardConfig;
	private final ReadFromFile readFile;
	private boolean gameEnded;
	private int activeCurls = 0;

	private List<Entity> entities;
	private List<Boat> boats;
	private List<Curl> curls;
	private List<Pole> poles;

	private int xDir[] = { -1, -1, -1 };
	private int yDir[] = { -1, 0, 1 };

	public Game() {
		readFile = new ReadFromFile(FILE_NAME);
		boardConfig = new BoardConfiguration();
		entities = readFile.getEntities();
		boats = new ArrayList<Boat>();
		curls = new ArrayList<Curl>();
		poles = new ArrayList<Pole>();
		splitArrays();
		gameEnded = false;
		runGame();
	}

	public void runGame() {

		Scanner read = new Scanner(System.in);
		
		boardConfig.updateBoard(entities);
		boardConfig.printBoard(new BoardPrinter());
		
		while (gameEnded == false && (read.nextLine().equals(""))) {
			
			manageCurls();
			manageBoats();
			
			if (curls.size() == activeCurls && boats.isEmpty()) {
				System.out.println(curls.size());
				gameEnded = true;
			}
			
			boardConfig.updateBoard(entities);
			boardConfig.printBoard(new BoardPrinter());
		}

	}

	private void manageBoats() {

		// move all the boats to the south
		Point boatPosition = new Point();
		Point newPosition = new Point();
		Iterator<Boat> it = boats.iterator();

		while(it.hasNext())
		{
			Boat b = it.next();
			boatPosition = b.getPosition();
			newPosition.x = boatPosition.x + 1;
			newPosition.y = boatPosition.y;

			if(isOnBoardPosition(newPosition) == false) {
				it.remove();
				entities.remove(b);
			}
			else {
				if (hitAPole(newPosition)) {
					System.out.println("Hit the pole");
					gameEnded = true;
					break;
				}
				else {
					b.move(newPosition);
					System.out.println(b.getPosition().x+ " " +b.getPosition().y);
				}
			}
		}
		
	}

	private boolean hitAPole(Point newPosition) {

		Point polePosition = new Point();

		for (Pole p : poles) {
			polePosition = p.getPosition();
			if (newPosition.x == polePosition.x && newPosition.y == polePosition.y)
				return true;
		}

		return false;

	}

	private void manageCurls() {

		// first move each active curl
		moveActiveCurls();
	}
	
	
	private void moveActiveCurls() {
		
		int direction = 0;
		Point newPosition = new Point();
		Point curlPosition = new Point();

		Iterator<Curl> it = curls.iterator();
		while (it.hasNext()) {
			Curl c = it.next();
			if (c.isOnRiver()) {
				curlPosition = c.getPosition();
				direction = generateNewDirection();
				newPosition.x = curlPosition.x + xDir[direction];
				newPosition.y = curlPosition.y + yDir[direction];

				if (isOnBoardPosition(newPosition)) {
					if (boardConfig.isOcupiedByBoat(newPosition)) {
						it.remove();
						removeBoat(newPosition);
						entities.remove(c);
					} else {
						c.move(newPosition);
					}
				} else {
					it.remove(); // the curl got out the board so it's not
									// existent anymore
					entities.remove(c);
				}

			}
		}
	}
	
	public void removeBoat(Point newPosition) {

		Point boatLocation; // remove the boat that got hit by the curl
		for (Boat b : boats) {
			boatLocation = b.getPosition();
			if (boatLocation.x == newPosition.x && boatLocation.y == newPosition.y) {
				boats.remove(b);
				entities.remove(b);
			}
		}
	}

	private boolean isOnBoardPosition(Point newPosition) {

		if (newPosition.x >= 0 && newPosition.y >= 0 && newPosition.x < boardConfig.ROWS
				&& newPosition.y < boardConfig.COLS)
			return true;

		return false;

	}

	private int generateNewDirection() {
		return (int) Math.random() * 3;
	}

	private void splitArrays() {
		for (Entity e : entities) {

			switch (e.getBoardRepresentation()) {
			case 'C': {
				curls.add((Curl) e);
				break;
			}
			case 'B': {
				boats.add((Boat) e);
				break;
			}
			case 'P': {
				poles.add((Pole) e);
				break;
			}
			}

		}
	}

}
