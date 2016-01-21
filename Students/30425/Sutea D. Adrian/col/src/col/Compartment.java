package col;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Compartment {
	private final int TICKET_PRICE = 100;
	private final int MAX_PASSENEGERS = 100;
	private ArrayList<Carriable> comp;
	private CargoItem cargo;
	private Passenger passenger;
	private int r;
	private int rand;
	private int items;
	private String x;
	private int noOfPassengers = 0;
	private int profit;

	public Compartment() {
		comp = new ArrayList<>();
		r = new Random().nextInt(100);
		r = Math.abs(r);
		for (int i = 0; i < r; i++) {
			rand = new Random().nextInt(1);
			rand = Math.abs(rand);
			if (rand == 1 && noOfPassengers <= MAX_PASSENEGERS) {
				passenger = new Passenger();
				x = UUID.randomUUID().toString();
				passenger.setPassengerName(x);
				comp.add(passenger);
				noOfPassengers++;
			} else {
				items = new Random().nextInt(1);
				items = Math.abs(rand);
				cargo = new CargoItem();
				x = UUID.randomUUID().toString();
				cargo.setCargoName(x);
				cargo.setProfit(r);
				cargo.setItemsNr(items);
				comp.add(cargo);
			}

		}

	}

	public int getProfit() {
		profit = noOfPassengers * TICKET_PRICE;
		return profit;
	}

}
