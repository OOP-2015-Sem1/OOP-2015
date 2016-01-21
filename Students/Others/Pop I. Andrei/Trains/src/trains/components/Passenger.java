package trains.components;

import java.util.UUID;

public class Passenger implements Carriable{
	
	private final String name;
	
	public Passenger() {
		name = UUID.randomUUID().toString();
	}

	public String getName() {
		return name;
	}
	
	
}
