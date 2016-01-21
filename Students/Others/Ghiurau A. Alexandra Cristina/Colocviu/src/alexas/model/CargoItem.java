package alexas.model;

public class CargoItem implements Carriable {

	private final int profit = 100;
	private String name = "CargoItem";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProfit() {
		return profit;
	}
}
