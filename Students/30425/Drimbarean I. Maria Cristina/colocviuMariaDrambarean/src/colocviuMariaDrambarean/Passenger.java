package colocviuMariaDrambarean;

import java.util.UUID;

public class Passenger implements Carriable {
	private final String name = UUID.randomUUID().toString();

	public String toString() {
		return name;
	}
}
