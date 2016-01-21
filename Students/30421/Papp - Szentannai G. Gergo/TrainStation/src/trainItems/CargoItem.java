package trainItems;

import java.util.Random;

public class CargoItem implements Carriable {
	Random random = new Random();
	private final String name = new String("Cargo " + random.nextInt());
	private final int profit = 135;

	public String getName() {
		return name;
	}

	public int getProfit() {
		return profit;
	}
}
