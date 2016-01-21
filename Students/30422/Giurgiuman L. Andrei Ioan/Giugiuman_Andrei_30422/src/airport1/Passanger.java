package airport1;

import java.util.UUID;

public class Passanger implements Carriable {
	private final String passangerName;

	public Passanger() {
		passangerName = UUID.randomUUID().toString();

	}

	public String getPassangerName() {
		return passangerName;

	}
}
