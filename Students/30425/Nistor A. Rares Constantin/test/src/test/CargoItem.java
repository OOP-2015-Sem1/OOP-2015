package test;

import java.util.*;

public class CargoItem implements Carriable {
	final String name = UUID.randomUUID().toString();
	final int profit = new Random().nextInt(100);

	public String getName() {
		return name;
	}

	public int getProfit() {
		return profit;
	}

}
