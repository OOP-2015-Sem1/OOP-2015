package battleship.background.code;

import static battleship.background.code.Constants.*;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

public class ShipMatrix implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean[][] matrix;
	public Point startingPoint;
	Point p;
	Ship ship;

	public ShipMatrix() {
		matrix = new boolean[BUTTONS_PER_LINE][BUTTONS_PER_LINE];
		for (int i = 0; i < BUTTONS_PER_LINE; i++) {
			for (int j = 0; j < BUTTONS_PER_LINE; j++) {
				matrix[i][j] = false;
			}
		}

	}

	public boolean isPlacementPermitted(Ship ship, Point startingPoint) {
		boolean permitted = true;
		if (ship.isPlacedHorizotally()) {
			if (startingPoint.y + ship.getLengthOfShip() > BUTTONS_PER_LINE) {
				return false;
			} else
				for (int i = startingPoint.y; i <= ship.getLengthOfShip() + startingPoint.y - 1; i++) {
					if (matrix[startingPoint.x][i] == true) {
						permitted = false;
					}
				}
		} else if (startingPoint.x + ship.getLengthOfShip() > BUTTONS_PER_LINE) {
			return false;
		} else
			for (int i = startingPoint.x; i <= ship.getLengthOfShip() + startingPoint.x - 1; i++) {
				if (matrix[i][startingPoint.y] == true) {
					permitted = false;
				}

			}
		return permitted;
	}

	public void placeShipInMatrix(Ship ship, Point startingPoint) {

		if (ship.isPlacedHorizotally()) {
			for (int i = startingPoint.y; i <= ship.getLengthOfShip() + startingPoint.y - 1; i++) {
				matrix[startingPoint.x][i] = true;
			}
		} else
			for (int i = startingPoint.x; i <= ship.getLengthOfShip() + startingPoint.x - 1; i++) {
				matrix[i][startingPoint.y] = true;
			}
	}

	public boolean[][] returnShipMatrix() {
		return matrix;
	}

	public boolean checkForWin() {
		boolean allShipsSank = true;
		for (int i = 0; i < BUTTONS_PER_LINE; i++) {
			for (int j = 0; j < BUTTONS_PER_LINE; j++) {
				if (matrix[i][j] == true) {
					allShipsSank = false;
				}
			}
		}
		return allShipsSank;
	}

	public void updateShipMatrixWithHits(int i, int j) {
		if (checkIfTrue(i, j)) {
			matrix[i][j] = false;
		}
	}

	public void updateHitsMatrixWithHits(int i, int j) {
		matrix[i][j] = true;
	}

	public boolean checkIfTrue(int i, int j) {
		return matrix[i][j];
	}

	public Point generateRandomStartingPoint() {
		Random r1 = new Random();
		Random r2 = new Random();
		p = new Point();
		p.x = r1.nextInt(10);
		p.y = r2.nextInt(10);
		return p;
	}

	public boolean generateRandomPosition() {
		Random r1 = new Random();
		if (r1.nextInt(2) == 1)
			return true;
		else {
			return false;
		}
	}

	public void initializeMatrixWithShips() {
		startingPoint = new Point(0, 0);
		ship = new Ship(11, generateRandomPosition());

		for (int lengthShip = 1; lengthShip <= 5; lengthShip++) {
			while (!isPlacementPermitted(ship, startingPoint)) {
				startingPoint = generateRandomStartingPoint();
				ship = new Ship(lengthShip, generateRandomPosition());
			}
			placeShipInMatrix(ship, startingPoint);
		}
	}
}
