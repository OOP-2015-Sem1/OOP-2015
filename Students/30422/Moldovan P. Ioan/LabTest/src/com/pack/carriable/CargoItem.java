package com.pack.carriable;

import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable {
	private final int profit;
	private final String name;

	public CargoItem() {
		name = UUID.randomUUID().toString();
		profit = Math.abs(new Random().nextInt(100));
	}

	public String getName() {
		return name;
	}

	public int getProfit() {
		return profit;
	}
}
