package model.cargo;

public class CargoItem implements Carriable {
	private final String name;
	private final int profit;
	
	public CargoItem(String name, int profit) {
		this.name = name;
		this.profit = profit;
	}

	public String getName() {
		return name;
	}

	public int getProfit() {
		return profit;
	}
}
