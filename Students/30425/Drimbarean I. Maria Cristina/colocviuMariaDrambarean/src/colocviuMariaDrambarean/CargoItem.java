package colocviuMariaDrambarean;

import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable {
	private final String name = UUID.randomUUID().toString();
	private final int profit = new Random().nextInt(50);

	public String toString() {
		return name;
	}

	public int getProfit() {
		return profit;
	}
}
