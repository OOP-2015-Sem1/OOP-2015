import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable {

	private final static int price = 200;

	private final String Cname = UUID.randomUUID().toString();
	private final int profit = getPrice();

	private static int getPrice() {
		Random rdm = new Random();
		int aux;
		aux = rdm.nextInt(price) + 1;
		return aux;
	}

	public String getName() {
		return Cname;
	}

	public int getProfit() {
		return profit;
	}

}
