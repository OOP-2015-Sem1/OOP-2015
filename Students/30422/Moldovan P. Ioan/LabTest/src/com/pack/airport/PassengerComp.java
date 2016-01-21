package com.pack.airport;

import com.pack.carriable.Passenger;

public class PassengerComp extends Compartment {
	public static final int MAX_PASSENGERS = 100;
	public static final int PRICE = 100;
	private int nrOfPassengers;

	public PassengerComp() {
		nrOfPassengers = 0;
	}

	protected int computeProfit() {
		return (objects.size() * nrOfPassengers);
	}

	public void addObject() {
		if (nrOfPassengers < MAX_PASSENGERS) {
			objects.add(new Passenger());
		}
	}

}
