package pachet.colocviu;
import java.util.UUID;

public class CargoItem implements Carriable {
	final String name;
	final  int profit;
	
	public CargoItem(){
		name = UUID.randomUUID().toString();
		profit = (int) Math.random();
	}
}
