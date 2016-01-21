package shipBayLabTest;

import java.util.ArrayList;
import java.util.UUID;

public class Compartment<T> {
	
	// collection of carriable
	public static final int MAX_NR_PASSENGERS = 100;
	public static final int PRICE_PASSENGER = 100;
	public final String ID;
	public final int type;
	public ArrayList<T> listOfItems;
	// public int nrCarriables;

	public Compartment(int type) {
		ID = UUID.randomUUID().toString();
		this.type = type;
		// nrCarriables = 0;
		// nrPassengers = Math.abs(new Random().nextInt(MAX_NR_PASSENGERS));
		listOfItems = new ArrayList<T>();
	}

	public void addCarriable(T carriable) {
		if (type == Ship.CARGO_TYPE || (type == Ship.PASS_TYPE && listOfItems.size() < MAX_NR_PASSENGERS)) {
			listOfItems.add(carriable);
			// nrCarriables++;
		}
	}

	public int getProfit(int profitCargo) {
		if (type == Ship.PASS_TYPE) {
			// return nrCarriables*PRICE_PASSENGER;
			return listOfItems.size() * PRICE_PASSENGER;
		} else {
			// return nrCarriables*listOfItems.get(0).profit;
			return listOfItems.size() * profitCargo;
		}
	}
}
