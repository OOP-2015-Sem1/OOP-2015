package bay;

import java.util.*;

import utilities.CargoType;

public class Compartment {
	private final String ID;
	private double profit;
	private CargoType type;

	public Compartment(CargoType type) {
		ID = UUID.randomUUID().toString();
		this.type = type;
	}

	public CargoType getType() {
		return type;
	}

	public void computeProfit() {
	}

	public double getProfit() {
		return profit;
	}
}
