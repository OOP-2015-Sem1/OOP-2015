package com.train.station;

import com.train.station.comparators.TrainNameComparator;
import com.train.station.comparators.TrainProfitComparator;
import com.train.station.entities.Train;
import com.train.station.entities.TrainStation;
import com.train.station.entities.wagons.CargoWagon;
import com.train.station.entities.wagons.PassengerWagon;
import com.train.station.factories.TrainFactory;

/**
 ******************************************************************************************************
 * EVERYTHING FROM THIS CLASS WOULD NOT HAVE BEEN TAKEN INTO CONSIDERATION IN THE THE GRADING PROCESS.*
 ******************************************************************************************************
 */
public class Main {

	public static void main(String[] args) {
		TrainStation trainStation = new TrainStation();
		TrainFactory trainFactory = new TrainFactory();

		Train<PassengerWagon> passengerTrainOne = trainFactory
				.createPassengerTrain();
		trainStation.addTrain(passengerTrainOne);

		Train<PassengerWagon> passengerTrainTwo = trainFactory
				.createPassengerTrain();
		trainStation.addTrain(passengerTrainTwo);

		Train<CargoWagon> cargoTrainOne = trainFactory.createCargoTrain();
		trainStation.addTrain(cargoTrainOne);

		// ! Checking 'isTrainInStation' method
		if (trainStation.isTrainInStation(passengerTrainTwo)) {
			System.out.println("Passenger train two is in the station");
		}

		if (trainStation.isTrainInStation(cargoTrainOne)) {
			System.out.println("Cargo train one is in the station");
		}

		Train<CargoWagon> cargoTrainNotAddedInStation = trainFactory
				.createCargoTrain();
		if (!trainStation.isTrainInStation(cargoTrainNotAddedInStation)) {
			System.out
					.println("Cargo train which was not added in the train station is not there.");
		}

		// ! checking 'removeTrain' method
		trainStation.removeTrain(passengerTrainTwo);
		if (!trainStation.isTrainInStation(passengerTrainTwo)) {
			System.out
					.println("Passenger train three was successfully removed from the train station.");
		}

		// ! Checking 'sorting by name'
		trainStation.printSortedTrains(new TrainNameComparator());

		// ! Checking 'sorting by profit'
		trainStation.printSortedTrains(new TrainProfitComparator());

		// ! checking 'trainInfo' method
		trainStation.trainInfo(passengerTrainOne);
	}

}
