package ship;

import java.util.UUID;

public class Compartment {
	public static final int TICKET_PRICE = 100;
	public static final int MAX_NUMBER_OF_PASSENGERS = 100;
	final String x = UUID.randomUUID().toString();
	CompartmentType type;
	private int profitP;
	public int nrOfPassengers;
	private String CargoItem;

	Compartment compartment = new Compartment(CompartmentType.DEFAULT);

	public Compartment(CompartmentType type) {
		if (type == CompartmentType.PASSENGER) {
			System.out.println("This is a passenger compartment.");
			System.out.println("This is the profit: " + profitP);
			compartment.increasePassengers();
		}

		if (type == CompartmentType.CARGO) {
			System.out.println("This is a cargo compartment.");
			setCargoItem("1");
		}
	}

	public CompartmentType getType() {
		return type;
	}

	public int profitPassenger() {
		profitP = TICKET_PRICE * nrOfPassengers;
		return profitP;
	}

	public int getNumberOfPassengers() {
		return nrOfPassengers;
	}

	public void increasePassengers() {
		nrOfPassengers++;
	}

	public void setCargoItem(String cargoItem) {
		CargoItem = cargoItem;
	}
}
