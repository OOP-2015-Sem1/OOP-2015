package pack;

public class CargoItem implements Carriable{
	
	private final String name;
	private final int profit;
	
	public CargoItem(String name, int profit){
		this.name = name;
		this.profit = profit;
	}
	
	public int getProfit(){
		return this.profit;
	}

}
