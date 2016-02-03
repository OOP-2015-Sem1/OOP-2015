package com.ship.bay;

import com.ship.bay.comparators.ShipNameComparator;
import com.ship.bay.comparators.ShipProfitComparator;
import com.ship.bay.entities.Ship;
import com.ship.bay.entities.ShipBay;
import com.ship.bay.entities.compartments.CargoCompartment;
import com.ship.bay.entities.compartments.PassengerCompartment;
import com.ship.bay.factories.ShipFactory;

/**
 ******************************************************************************************************
 * EVERYTHING FROM THIS CLASS WOULD NOT HAVE BEEN TAKEN INTO CONSIDERATION IN THE THE GRADING PROCESS.*
 ******************************************************************************************************
 */
public class Main {

	public static void main(String[] args) {
		ShipBay shipBay = new ShipBay();
		ShipFactory shipFactory = new ShipFactory();

		Ship<PassengerCompartment> passengerShipOne = shipFactory
				.createPassengerShip();
		shipBay.addShip(passengerShipOne);

		Ship<PassengerCompartment> passengerShipTwo = shipFactory
				.createPassengerShip();
		shipBay.addShip(passengerShipTwo);

		Ship<CargoCompartment> cargoShipOne = shipFactory.createCargoShip();
		shipBay.addShip(cargoShipOne);

		// ! Checking 'isShipInStation' method
		if (shipBay.isShipInBay(passengerShipTwo)) {
			System.out.println("Passenger Ship two is in the ship bay");
		}

		if (shipBay.isShipInBay(cargoShipOne)) {
			System.out.println("Cargo Ship one is in the ship bay");
		}

		Ship<CargoCompartment> cargoShipNotAddedInStation = shipFactory
				.createCargoShip();
		if (!shipBay.isShipInBay(cargoShipNotAddedInStation)) {
			System.out
					.println("Cargo Ship which was not added in the ship bay is not there.");
		}

		// ! checking 'removeShip' method
		shipBay.removeShip(passengerShipTwo);
		if (!shipBay.isShipInBay(passengerShipTwo)) {
			System.out
					.println("Passenger Ship three was successfully removed from the ship bay.");
		}

		// ! Checking 'sorting by name'
		shipBay.printSortedShips(new ShipNameComparator());

		// ! Checking 'sorting by profit'
		shipBay.printSortedShips(new ShipProfitComparator());

		// ! checking 'shipInfo' method
		shipBay.shipInfo(passengerShipOne);
	}

}
