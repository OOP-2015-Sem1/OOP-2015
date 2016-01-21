package pac1;

public class CargoItem implements Carriable {
	public final String NAME;
	public final int profit;

	CargoItem(String name, int profit) {
		super();
		this.NAME = name;
		this.profit = profit;
	}

	public int getProfit() {
		return profit;
	}

}
