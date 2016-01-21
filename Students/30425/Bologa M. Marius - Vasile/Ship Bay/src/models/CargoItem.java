package models;

public class CargoItem implements Carriable{
	private final String name;
	private  final int profit;
	public CargoItem(String n, int p) {
		this.name=n;
		this.profit=p;
		
	}
	public String getName() {
		return name;
	}
	public int getProfit() {
		return profit;
	}


}
