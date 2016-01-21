package model.wagon;

import model.carriable.Carriable;
import model.carriable.Passenger;

public class PassengerWagon extends Wagon {

	private static final int TICKET_PRICE = 100;
	private static final int MAX_PASSENGER_NO = 100;
	
	public PassengerWagon(String id) {
		super(id);
	}

	@Override
	public int getProfit() {
		return TICKET_PRICE * carry.size();
	}
	
	public void addPassenger(Passenger newPassenger) {
		if (getCarrySize() > MAX_PASSENGER_NO) {
			System.out.println("No room left for passengers in wagon " + id);
		} else if (newPassenger == null) {
			System.out.println("Invalid passenger");			
		} else {
			carry.add((Carriable)newPassenger);
		}
	}
	
	public void removePassenger(Passenger toRemove) {
		carry.remove(toRemove);
	}

}
