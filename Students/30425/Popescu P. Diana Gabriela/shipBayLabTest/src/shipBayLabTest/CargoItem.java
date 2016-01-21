package shipBayLabTest;

public class CargoItem implements Carriable {

	public final String name;
	public final int profit;

	public CargoItem(String name, int profit) {
		this.name = name;
		this.profit = profit;
	}
}
