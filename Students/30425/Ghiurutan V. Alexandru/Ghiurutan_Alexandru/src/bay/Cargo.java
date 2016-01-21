package bay;

import java.util.ArrayList;

import utilities.CargoType;

public class Cargo extends Compartment implements Carriable {
	public double profit;
	private final CargoItem item;
	private ArrayList<Carriable> objects;

	public Cargo(CargoItem item) {
		super(CargoType.CARGO);
		objects = new ArrayList<Carriable>();
		this.item = item;
	}

	public void addObjects(Cargo p) {
		objects.add(p);
		computeProfit();
	}

	public void computeProfit() {
		this.profit = objects.size() * item.getProfit();
	}
}
