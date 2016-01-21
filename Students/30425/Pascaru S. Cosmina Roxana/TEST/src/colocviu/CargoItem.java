package colocviu;

import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable {
	private final String name = UUID.randomUUID().toString();
	private static int profit = Math.abs(new Random().nextInt(100));

	public int getProfit() {
		return profit;
	}

	public void printName() {
		System.out.println(name);
	}

	public void printProfit() {
		System.out.println(profit);
	}

}
