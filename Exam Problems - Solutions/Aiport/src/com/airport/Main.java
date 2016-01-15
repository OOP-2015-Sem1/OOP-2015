package com.airport;

import com.airport.comparators.AirplaneNameComparator;
import com.airport.comparators.AirplaneProfitComparator;
import com.airport.entities.Airplane;
import com.airport.entities.Airport;
import com.airport.entities.compartments.CargoCompartment;
import com.airport.entities.compartments.PassengerCompartment;
import com.airport.factories.AirplaneFactory;

/**
 ******************************************************************************************************
 * EVERYTHING FROM THIS CLASS WOULD NOT HAVE BEEN TAKEN INTO CONSIDERATION IN THE THE GRADING PROCESS.*
 ******************************************************************************************************
 */
public class Main {

	public static void main(String[] args) {
		Airport airport = new Airport();
		AirplaneFactory airplaneFactory = new AirplaneFactory();

		Airplane<PassengerCompartment> passengerAirplaneOne = airplaneFactory
				.createPassengerAirplane();
		airport.addAirplane(passengerAirplaneOne);

		Airplane<PassengerCompartment> passengerAirplaneTwo = airplaneFactory
				.createPassengerAirplane();
		airport.addAirplane(passengerAirplaneTwo);

		Airplane<CargoCompartment> cargoAirplaneOne = airplaneFactory
				.createCargoAirplane();
		airport.addAirplane(cargoAirplaneOne);

		// ! Checking 'isAirplaneInStation' method
		if (airport.isAirplaneInAirport(passengerAirplaneTwo)) {
			System.out.println("Passenger Airplane two is in the airport");
		}

		if (airport.isAirplaneInAirport(cargoAirplaneOne)) {
			System.out.println("Cargo Airplane one is in the airport");
		}

		Airplane<CargoCompartment> cargoAirplaneNotAddedInStation = airplaneFactory
				.createCargoAirplane();
		if (!airport.isAirplaneInAirport(cargoAirplaneNotAddedInStation)) {
			System.out
					.println("Cargo Airplane which was not added in the Airport is not there.");
		}

		// ! checking 'removeAirplane' method
		airport.removeAirplane(passengerAirplaneTwo);
		if (!airport.isAirplaneInAirport(passengerAirplaneTwo)) {
			System.out
					.println("Passenger Airplane three was successfully removed from the Airport.");
		}

		// ! Checking 'sorting by name'
		airport.printSortedAirplanes(new AirplaneNameComparator());

		// ! Checking 'sorting by profit'
		airport.printSortedAirplanes(new AirplaneProfitComparator());

		// ! checking 'airplaneInfo' method
		airport.airplaneInfo(passengerAirplaneOne);
	}

}
