import java.util.Random;
import java.util.UUID;

public class Passenger implements Carriable {
	private final String name;
	public Passenger (String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	
	
}
