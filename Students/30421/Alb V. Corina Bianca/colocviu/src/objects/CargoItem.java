package objects;

import java.util.UUID;

import interfaces.Carriable;

public class CargoItem implements Carriable{
	
	private final String name = UUID.randomUUID().toString();
	private final int profit = UUID.randomUUID().hashCode();
	
	public String getName() {
		return name;
	}
	public int getProfit() {
		return profit;
	}
	
}
