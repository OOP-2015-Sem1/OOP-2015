package ship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main implements Compare {
	public static final int MAX_NUMBER_OF_PASSENGERS = 100;

	public static void main(String[] args) {
		Set<Ship> ships = new HashSet<Ship>();

		List<Compartment> passengerCompartments = new LinkedList<Compartment>();
		List<Compartment> cargoCompartments = new LinkedList<Compartment>();

		List<Passenger> passengers = new ArrayList<Passenger>();
		addPassengers(passengers);
	}

	public static void addPassengers(List<Passenger> passengers) {
		Passenger passenger = new Passenger();
		for (int i = 0; i <= MAX_NUMBER_OF_PASSENGERS; i++) {
			passenger.setName("Passenger " + i);
			passengers.add(passenger);
			System.out.println("" + passenger.getName());

		}
	}

	@Override
	public int compareTo(Ship ship) {
		// TODO Auto-generated method stub
		return 1;
	}

}
