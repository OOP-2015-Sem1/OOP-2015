package model.carriable;

public class CargoItem {
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
	
	public boolean equals(Object o) {
		CargoItem ci = (CargoItem) o;
		return ci.getName().equals(ci.getName());
	}
	
	public CargoItem copy() {
		return new CargoItem(name, profit);
	}
	
}
