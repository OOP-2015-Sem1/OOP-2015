package source;

import java.util.*;

public class Wagon<T> {

	public final int PASSENGER_WAGON_PRICE = 100;
	public final int MAX_PASSENGERS = 100;
	public final int NO_PROFIT = 0;
	private final String ID = UUID.randomUUID().toString();
	public List<Passenger> passengerWagon = new ArrayList<Passenger>();

	public void addPassenger(Passenger passenger) {
		if ((passengerWagon.size() < (MAX_PASSENGERS - 1)) && (passenger != null)) {
			passengerWagon.add(passenger);
		}
	}

	public int calculateProfitPassenger(List<Passenger> wagons) {
		if (wagons != null) {
			return (PASSENGER_WAGON_PRICE * wagons.size());
		} else {
			return (NO_PROFIT);
		}
	}
}