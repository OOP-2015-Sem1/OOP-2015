import java.util.UUID;

public class CargoItem implements Carriable {

	private final String name;
	private final int profit;
	public CargoItem(){
	
		this.name=UUID.randomUUID().toString();
		///Or some random way to generating the profit
		this.profit=32;
		
	}
	
	public int getProfit(){
		return this.profit;
	}
	
	public String getName(){
		return this.name;
	}
	
}
