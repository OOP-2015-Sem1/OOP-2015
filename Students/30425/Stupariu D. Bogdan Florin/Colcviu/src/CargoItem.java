import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable {
	public final String name = UUID.randomUUID().toString();
	public final int profit = Math.abs(new Random().nextInt(100));
	
	public int getProfit()
	{
		return profit;
	}
	
}
