import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable{
	private final String name;
	private final int profit;
	
	public CargoItem (String name, int profit) {
		this.name = name;
		this.profit = profit;
	}

	public String getName() {
		return name;
	}

	public int getProfit() {
		return profit;
	}
	
	public CargoItem returnCarriable (){
		Random rndm = new Random();
		return new CargoItem(UUID.randomUUID().toString(),rndm.nextInt(100));
	}
}
