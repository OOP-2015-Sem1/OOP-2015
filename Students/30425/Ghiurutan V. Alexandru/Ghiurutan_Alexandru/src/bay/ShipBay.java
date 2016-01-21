package bay;

import java.util.*;

public class ShipBay {
	private Set<Ship> ship;

	public ShipBay() {
		ship = new HashSet<Ship>();
	}

	public void addShip(Ship s) {
		ship.add(s);
	}

	public void remove(Ship s) {
		ship.remove(s);
	}

	public boolean contain(Ship s) {
		return ship.contains(s);
	}
}
