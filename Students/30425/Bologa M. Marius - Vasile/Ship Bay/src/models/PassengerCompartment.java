package models;

import java.util.*;

public class PassengerCompartment extends Compartment {
	public List<Carriable> passengerObjects = new ArrayList<Carriable>();
	Passenger[] paray = { new Passenger("Ion"), new Passenger("Mihai"), new Passenger("Ioana") };
	public List<Passenger> passenger = Arrays.asList(paray);

	public PassengerCompartment(String id) {
		super(id);
	}

	@Override
	public int computeProfit() {
		int profit = passenger.size() * Constants.ticket;
		return profit;
	}
	public static void addPassenger(ArrayList<Passenger> a){
		if(a.size()>Constants.maxPassengers){
			System.out.println("Sorry we have no more places!");
		}
		else{a.add(new Passenger("ioN"));}
	}

}
