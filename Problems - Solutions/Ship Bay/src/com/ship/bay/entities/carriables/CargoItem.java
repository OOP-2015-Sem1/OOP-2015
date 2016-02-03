package com.ship.bay.entities.carriables;

public class CargoItem implements Carriable {

	private final String name;
	private final int profit;

	public CargoItem(String name, int profit) {
		this.profit = profit;
		this.name = name;
	}

	public int getProfit() {
		return profit;
	}

	public String getName() {
		return name;
	}

}
