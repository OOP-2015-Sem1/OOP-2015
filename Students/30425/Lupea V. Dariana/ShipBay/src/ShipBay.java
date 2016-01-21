import java.util.HashSet;
import java.util.Set;

public class ShipBay {

	Set<Ship> shipSet = new HashSet<Ship>();//unique ships

	private void receiveShip(Ship x) {
		shipSet.add(x);
	}

	private void departShip(Ship y) {
		shipSet.remove(y);
	}

	private boolean checkShip(Ship z) {
		if (shipSet.contains(z))
			return true;
		else
			return false;

	}

}
