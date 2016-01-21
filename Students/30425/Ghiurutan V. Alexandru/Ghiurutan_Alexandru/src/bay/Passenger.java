package bay;

import java.util.ArrayList;

import utilities.CargoType;

public class Passenger extends Compartment implements Carriable {
	private final String name;
	private static final double PRICE = 100;
	private double profit;
	private ArrayList<Carriable> objects;

	public Passenger(String name) {
		super(CargoType.PASSENGER);
		this.name = name;
		objects = new ArrayList<Carriable>();
	}

	public void addObjects(Passenger p) {
		if (objects.size() == 100) {
			return;
		} else {
			objects.add(p);
			computeProfit();
		}
	}

	public void computeProfit() {
		this.profit = objects.size() * PRICE;
	}
}
