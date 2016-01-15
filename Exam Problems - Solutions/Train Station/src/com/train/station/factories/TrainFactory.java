package com.train.station.factories;

import java.util.Random;
import java.util.UUID;

import com.train.station.entities.Train;
import com.train.station.entities.carriables.CargoItem;
import com.train.station.entities.carriables.Carriable;
import com.train.station.entities.carriables.Passenger;
import com.train.station.entities.wagons.CargoWagon;
import com.train.station.entities.wagons.PassengerWagon;

/**
 ******************************************************************************************************
 * EVERYTHING FROM THIS CLASS WOULD NOT HAVE BEEN TAKEN INTO CONSIDERATION IN THE THE GRADING PROCESS.*
 ******************************************************************************************************
 */
public class TrainFactory {

	private static final int MAX_CARGO_ITEM_PROFIT = 250;
	private static final int MAX_CARGO_ITEM_NAME_LENGTH = 10;
	private static final int MAX_TRAIN_NAME_LENGTH = 15;
	private static final int MAX_NUMBER_OF_WAGONS_PER_TRAIN = 30;
	private static final int MAX_NUMBER_OF_ATTEMPTED_PASSENGERS = 200;
	private static final int MAX_NUMBER_OF_ITEMS_PER_CARGO_WAGON = 100;

	public Train<PassengerWagon> createPassengerTrain() {
		Train<PassengerWagon> passengerTrain = new Train<PassengerWagon>(UUID
				.randomUUID().toString().substring(0, MAX_TRAIN_NAME_LENGTH));

		int numberOfWagons = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_WAGONS_PER_TRAIN;
		for (int i = 0; i < numberOfWagons; i++) {
			passengerTrain.addWagon(createPassengerWagon());
		}

		return passengerTrain;
	}

	public Train<CargoWagon> createCargoTrain() {
		Train<CargoWagon> cargoTrain = new Train<CargoWagon>(UUID.randomUUID()
				.toString().substring(0, MAX_TRAIN_NAME_LENGTH));
		int numberOfWagons = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_WAGONS_PER_TRAIN;
		for (int i = 0; i < numberOfWagons; i++) {
			cargoTrain.addWagon(createCargoWagon());
		}

		return cargoTrain;
	}

	private PassengerWagon createPassengerWagon() {
		PassengerWagon passengerWagon = new PassengerWagon();

		int numberOfPassengers = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_ATTEMPTED_PASSENGERS;
		for (int i = 0; i < numberOfPassengers; i++) {
			passengerWagon.addCarriable(createPassenger());
		}
		return passengerWagon;
	}

	private CargoWagon createCargoWagon() {
		CargoItem cargoItem = createCargoItem();
		CargoWagon cargoWagon = new CargoWagon(cargoItem);
		int numberOfItemsCarried = Math.abs(new Random().nextInt())
				% MAX_NUMBER_OF_ITEMS_PER_CARGO_WAGON;
		for (int i = 0; i < numberOfItemsCarried; i++) {
			cargoWagon.addCarriable(cargoItem);
		}
		return cargoWagon;
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
