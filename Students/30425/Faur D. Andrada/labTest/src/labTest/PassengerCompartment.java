package labTest;

import java.util.ArrayList;

public class PassengerCompartment implements Compartment {
	final String cargoID = Compartment.getID();
	public ArrayList<Carriable> passengerCompartments;
	final int profit = Compartment.generateProfit(100, passengerCompartments.size());

}
