package shipBayLabTest;

import java.util.ArrayList;

public class Ship {

	public static final int PASS_TYPE = 0;
	public static final int CARGO_TYPE = 1;

	public final String name;
	public final int type;
	public ArrayList<Compartment> listOfCompartments;

	public Ship(String name, int type) {
		this.name = name;
		// this.type=Math.abs(new Random().nextInt(1));
		this.type = type;
		listOfCompartments = new ArrayList<Compartment>();
	}

	public void addCompartment() {
		if (type == Ship.CARGO_TYPE) {
			listOfCompartments.add(new Compartment<CargoItem>(type));
		} else if (type == Ship.PASS_TYPE) {
			listOfCompartments.add(new Compartment<Passenger>(type));
		}
	}

	public int getProfit() {
		int sum = 0;
		for (int i = 0; i < listOfCompartments.size(); i++) {
			sum += listOfCompartments.get(i).getProfit(0); // !
		}
		return sum;
	}
}
