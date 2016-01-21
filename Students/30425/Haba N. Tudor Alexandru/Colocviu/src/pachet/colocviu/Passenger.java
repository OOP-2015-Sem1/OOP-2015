package pachet.colocviu;
import java.util.UUID;

public class Passenger implements Carriable {
	final String name;
	final int price=25;
	
	public Passenger() {
		name= UUID.randomUUID().toString();
	}
}
