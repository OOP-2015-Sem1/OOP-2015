package source;

import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable{
	

	private Random r= new Random();
	
	public final String name;
	public final int profit;
	
	public CargoItem(){
		this.name=UUID.randomUUID().toString();
		this.profit= r.nextInt(100);
	}
	
	public String getName() {
		return name;
	}
	public int getProfit() {
		return profit;
	}
	
}
