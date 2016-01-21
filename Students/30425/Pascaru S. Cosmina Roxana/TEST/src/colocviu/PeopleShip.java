package colocviu;

import java.util.UUID;

public class PeopleShip extends Ship {
	private String name = UUID.randomUUID().toString();

	@Override
	public void computeProfit() {
		for (int i = 0; i < shipCompartments.size(); i++) {
			
		}

	}

	public void showName() {
		System.out.println(name);
	}

	@Override
	public void showNumberOfCompartments() {
		int number = shipCompartments.size();
		System.out.println(number);

	}

}
