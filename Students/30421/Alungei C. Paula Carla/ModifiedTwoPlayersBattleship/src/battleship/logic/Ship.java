package battleship.logic;

import java.util.Random;

public class Ship {

	private int[] ships;

	private int killedShips = 0;

	public Ship(boolean mainPlayer) {
		ships = new int[3];
		for (int ship = 0; ship < 3; ship++) {
			if (!mainPlayer) {
				initSecondPlayerType(ship);
			}
		}
	}

	public void initMainPlayerType(int ship, int position) {

		ships[ship] = position;

	}

	private void initSecondPlayerType(int ship) {
		Random random = new Random();
		ships[ship] = random.nextInt(25);

		for (int last = 0; last < ship; last++) {
			if ((ships[ship] == ships[last]) && (ships[ship] == ships[last])) {
				do {
					ships[ship] = random.nextInt(25);

				} while ((ships[ship] == ships[last]));
			}
		}
	}

	public void incrementKilledShips() {
		killedShips++;
	}

	// getters and setters
	public int[] getShips() {
		return ships;
	}

	public void setShips(int[] ships) {
		this.ships = ships;
	}

	public int getKilledShips() {
		return killedShips;
	}

	public void setKilledShips(int killedShips) {
		this.killedShips = killedShips;
	}

}
