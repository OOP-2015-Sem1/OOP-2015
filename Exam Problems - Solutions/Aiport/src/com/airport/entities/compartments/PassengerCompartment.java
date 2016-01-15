package com.airport.entities.compartments;

import com.airport.entities.carriables.Carriable;


public class PassengerCompartment extends Compartment {

	private static final int TICKET_PRICE = 100;
	private static final int MAX_PASSENGERS = 100;

	@Override
	public void addCarriable(Carriable c) {
		if (items.size() < MAX_PASSENGERS)
			super.addCarriable(c);
	}

	@Override
	public int getProfit() {
		return TICKET_PRICE * items.size();
	}
}
