package labtest;

import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable {

	public static final String itemName = UUID.randomUUID().toString();
	public static final int profit = Math.abs(new Random().nextInt(50));
	
}
