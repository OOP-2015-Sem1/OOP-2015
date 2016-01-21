

public class CargoItem implements Carriable {

	private int profit = 200;

	private String name = new String();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getProfit() {
		return this.profit;
	}

	public CargoItem(String name, int profit) {
		setName(name);
		setProfit(profit);
	}

	@Override
	public void type(String name, int profit) {
		Carriable cargo1 = new CargoItem("Deluxe", 250);

	}

}
