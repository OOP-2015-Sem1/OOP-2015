package battleship.logic;

import java.util.Random;

public class Shoot {

	int shoot;

	public int shootIt() {

		Random random = new Random();
		shoot = random.nextInt(25);
		return shoot;
	}

	public boolean hit(Ship ships, boolean mainPlayer, boolean msgDysplay) {

		for (int ship = 0; ship < ships.getShips().length; ship++) {
			if (shoot == ships.getShips()[ship]) {
				if (msgDysplay) {
					if (mainPlayer) {

						// System.out.printf("Player2 hit a ship located in
						// (%d,%d)\n",shoot[0]+1,shoot[1]+1);
					} else {
						// System.out.printf("You hit a ship located in
						// (%d,%d)\n",shoot[0]+1,shoot[1]+1);
					}
				}
				return true;
			}
		}
		return false;
	}

	// getters and setters
	public int getShoot() {
		return shoot;
	}

	public void setShoot(int shoot) {
		this.shoot = shoot;
	}

}
