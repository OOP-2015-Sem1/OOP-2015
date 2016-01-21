import java.util.Random;
import java.util.UUID;

public class CargoItem implements Carriable {
	
	public final String name=UUID.randomUUID().toString();
	
	public final int profit = new Random().nextInt(500);
	
	
}
