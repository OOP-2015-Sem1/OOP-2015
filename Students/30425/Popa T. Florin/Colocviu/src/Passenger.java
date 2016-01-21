import java.util.Random;

public class Passenger implements Carriable {

	private final String name = "John Doe";
	public int profit = 100;
	Random r = new Random();
	int number = r.nextInt(100);
	
	public int getNumber() {
		return number;
	}
	
	public int getProfit() {
		return profit;
	}
	
}
