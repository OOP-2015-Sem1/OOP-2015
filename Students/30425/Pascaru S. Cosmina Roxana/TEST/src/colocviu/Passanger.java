package colocviu;

import java.util.UUID;

public class Passanger implements Carriable {
	private String name = UUID.randomUUID().toString();

	public void printName() {
		System.out.println(name);
	}

}
