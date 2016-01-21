package colocviuMariaDrambarean;

import java.util.LinkedHashSet;

public class Ship {
	private String name;
	private int profit = 0;
	private LinkedHashSet<Compartment> compartments;

	public void computeProfit() {
		for (Compartment c : compartments) {
			profit += c.getProfit();
		}
	}

	public void add(Compartment c) {
		compartments.add(c);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public LinkedHashSet<Compartment> getCompartments() {
		return compartments;
	}

	public void setCompartments(LinkedHashSet<Compartment> compartments) {
		this.compartments = compartments;
	}
}
