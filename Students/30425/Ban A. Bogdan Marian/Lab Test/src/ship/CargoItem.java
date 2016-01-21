package ship;

public class CargoItem implements Carriable {
	private String name;
	private int profit;
	
	public final String getName(){
		return name;
	}
	
	public final int getProfit(){
		return profit;
	}
}
