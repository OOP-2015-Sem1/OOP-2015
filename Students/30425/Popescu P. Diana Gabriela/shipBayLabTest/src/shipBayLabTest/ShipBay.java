package shipBayLabTest;

import java.util.ArrayList;

public class ShipBay {

	public final String name;
	public ArrayList<Ship> ships;

	public ShipBay(String name) {
		this.name = name;
	}

	public void receiveShip(Ship ship) {
		ships.add(ship);
	}

	public void departShip(Ship ship) {
		boolean found = false;
		for (int i = 0; !found && i < ships.size(); i++) {
			if (ship == ships.get(i)) {
				found = true;
				ships.remove(i);
			}
		}
	}
	public boolean isInBay(Ship ship){
		for (int i = 0; i < ships.size(); i++) {
			if (ship == ships.get(i)) {
				return true;
			}
		}
		return false;
	}
}
