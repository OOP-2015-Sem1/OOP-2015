package colocviu;

import java.util.HashSet;
import java.util.Set;

public class ShipBay {

	Set<Ship> shipSet = new HashSet<Ship>();

	public <Ship> void receive(Ship s) {
		shipSet.add((colocviu.Ship) s);
	}

	public <Ship> void departShip(Ship s) {
		shipSet.remove(s);
	}

	public <Ship> boolean checkForShip(Ship s) {
		if (shipSet.contains(s)) {
			return true;
		} else
			return false;
	}

}
