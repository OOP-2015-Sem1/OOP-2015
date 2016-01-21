package objects;

import java.util.UUID;

import interfaces.Carriable;

public class Passenger implements Carriable{
	
	private final String name = UUID.randomUUID().toString();

	public String getName() {
		return name;
	}
}
