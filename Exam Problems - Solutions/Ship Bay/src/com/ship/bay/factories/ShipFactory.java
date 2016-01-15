package com.ship.bay.factories;

import java.util.Random;
import java.util.UUID;

import com.ship.bay.entities.Ship;
import com.ship.bay.entities.carriables.CargoItem;
import com.ship.bay.entities.carriables.Carriable;
import com.ship.bay.entities.carriables.Passenger;
import com.ship.bay.entities.compartments.CargoCompartment;
import com.ship.bay.entities.compartments.PassengerCompartment;

/**
 ******************************************************************************************************
 * EVERYTHING FROM THIS CLASS WOULD NOT HAVE BEEN TAKEN INTO CONSIDERATION IN THE THE GRADING PROCESS.*
 ******************************************************************************************************
 */
public class ShipFactory {

	private static final int MAX_CARGO_ITEM_PROFIT = 250;
	private static final int MAX_CARGO_ITEM_NAME_LENGTH = 10;
	private static final int MAX_SHIP_NAME_LENGTH = 15;
	private static final int MAX_NUMBER_OF_COMPARTMENTS_PER_TRAIN = 30;
	private static final int MAX_NUMBER_OF_ATTEMPTED_PASSENGERS = 200;
	private static final int MAX_NUMBER_OF_ITEMS_PER_CARGO_COMPARTMENT = 100;

	public Ship<PassengerCompartment> createPassengerShip() {
		Ship<PassengerCompartment> passengerShip = new Ship<PassengerCompartment>(
				UUID.randomUUID().toString()
						.substring(0, MAX_SHIP_NAME_LENGTH));

		int numberOfCompartments = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_COMPARTMENTS_PER_TRAIN;
		for (int i = 0; i < numberOfCompartments; i++) {
			passengerShip.addCompartment(createPassengerCompartment());
		}

		return passengerShip;
	}

	public Ship<CargoCompartment> createCargoShip() {
		Ship<CargoCompartment> cargoShip = new Ship<CargoCompartment>(UUID
				.randomUUID().toString().substring(0, MAX_SHIP_NAME_LENGTH));
		int numberOfCompartments = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_COMPARTMENTS_PER_TRAIN;
		for (int i = 0; i < numberOfCompartments; i++) {
			cargoShip.addCompartment(createCargoCompartment());
		}

		return cargoShip;
	}

	private PassengerCompartment createPassengerCompartment() {
		PassengerCompartment passengerCompartment = new PassengerCompartment();

		int numberOfPassengers = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_ATTEMPTED_PASSENGERS;
		for (int i = 0; i < numberOfPassengers; i++) {
			passengerCompartment.addCarriable(createPassenger());
		}
		return passengerCompartment;
	}

	private CargoCompartment createCargoCompartment() {
		CargoItem cargoItem = createCargoItem();
		CargoCompartment cargoCompartment = new CargoCompartment(cargoItem);
		int numberOfItemsCarried = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_ITEMS_PER_CARGO_COMPARTMENT;
		for (int i = 0; i < numberOfItemsCarried; i++) {
			cargoCompartment.addCarriable(cargoItem);
		}
		return cargoCompartment;
	}

	private Carriable createPassenger() {
		return new Passenger(String.valueOf(new Random().nextInt()));
	}

	private CargoItem createCargoItem() {
		return new CargoItem(UUID.randomUUID().toString()
				.substring(0, MAX_CARGO_ITEM_NAME_LENGTH),
				Math.abs(new Random().nextInt(MAX_CARGO_ITEM_PROFIT)));
	}
}
