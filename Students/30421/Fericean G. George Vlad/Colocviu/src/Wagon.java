import java.util.UUID;

import carriable.CargoItem;
import carriable.Passenger;

public class Wagon<String> {
	public int ticketPrice = 100;
	public int maxPass = 100;
	public int profit = 0;
	public int nrPass = 0;
	public int nrCargo = 0;
	public int n = 150;
	String uid = (String) UUID.randomUUID();

	public void passWagon() {
		for (int i = 0; i <= n; i++) {
			if (nrPass > maxPass) {
				System.out.println("max number of passengers reached");
			} else {

				Passenger pass1 = new Passenger();
				nrPass++;
			}
			profit = nrPass * ticketPrice;
		}
	}

	public void cargoWagon() {
		CargoItem item1 = new CargoItem();
		nrCargo++;
		profit = nrCargo * item1.getProfit();
	}
}
