package bay;

import java.util.*;

import utilities.CargoType;

public class Ship {
	private Set<Compartment> compartments;
	private CargoType type;
	private String name;
	private double profit = 0;

	public Ship(CargoType type) {
		compartments = new LinkedHashSet<Compartment>();
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addCompartment(Compartment c) {
		if (compartments.size() == 0) {
			this.type = c.getType();
		}
		if (c.getType() == type) {
			compartments.add(c);
			computeProfit(c.getProfit());
		}

	}

	public void computeProfit(double p) {
		this.profit += p;
	}

	public double getTotalProfitOfAShip() {
		return profit;
	}
}
