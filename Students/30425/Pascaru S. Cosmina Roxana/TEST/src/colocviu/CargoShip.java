package colocviu;

import java.util.UUID;

public class CargoShip extends Ship {
	private String name = UUID.randomUUID().toString();

	@Override
	public void computeProfit() {

	}

	@Override
	public void showName() {
		System.out.println(name);

	}

	@Override
	public void showNumberOfCompartments() {
		int number = shipCompartments.size();
		System.out.println(number);

	}

}
