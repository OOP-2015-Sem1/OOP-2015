package models;

import java.util.*;

public class CargoCompartment extends Compartment {
	public List<Carriable> cargoObjects = new ArrayList<Carriable>();
	CargoItem item = new CargoItem("Corn", 20);

	public CargoCompartment(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int computeProfit() {
		int profit = cargoObjects.size() * item.getProfit();
		return profit;
	}

}
