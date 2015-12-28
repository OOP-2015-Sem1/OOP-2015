package battleship.background.code;

import static battleship.background.code.Constants.*;

import java.awt.Point;
import java.io.Serializable;

public class ShipMatrix implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean[][] matrix;
	public Point startingPoint;

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
	public boolean checkIfTrue(int i, int j) {
		return matrix[i][j];
	}

	
}
