package labTest;

import java.util.ArrayList;

public class CargoCompartment implements Compartment {
	final String cargoID = Compartment.getID();
	public ArrayList<Carriable> cargoCompartments;
	int cargoItem;
	final int profit = Compartment.generateProfit(cargoItem, cargoCompartments.size());

}
