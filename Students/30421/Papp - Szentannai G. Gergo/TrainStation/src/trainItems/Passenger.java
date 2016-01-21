package trainItems;

import java.util.Random;

public class Passenger implements Carriable {
	Random random = new Random();
	private final String name = new String("Passenger " + random.nextInt());
	private final int profit = 100;
	public String getName() {
		return name;
	}
	@Override
	public int getProfit() {
		// TODO Auto-generated method stub
		return 0;
	}

}
