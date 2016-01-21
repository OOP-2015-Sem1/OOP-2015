package main;

import java.util.LinkedList;
import java.util.Random;

import model.cargo.CargoItem;
import model.cargo.Passenger;
import model.train.Train;
import model.wagon.CargoWagon;
import model.wagon.PassangerWagon;
import model.wagon.Wagon;
import utilities.ScreenPrinter;

public final class MainClass {

	public static void main(String[] args) {
		/* Testing */
		TrainStation trainStation = new TrainStation();

		Random r = new Random();
		// Train 1
		Train<PassangerWagon> train1 = new Train<>("Passanger train 0");
		for (int i = 0; i < 5; i++) {
			PassangerWagon wagon = new PassangerWagon();
			for (int j = 0; j < 15; j++) {
				wagon.addCarriable(new Passenger("Bob"));
			}
			train1.addWagon(wagon);
		}
		trainStation.receiveTrain(train1);

		// generate random trains
		LinkedList<Train<Wagon>> trains = new LinkedList<>();
		for (int nTrains = 0; nTrains < 50; nTrains++) {
			Train<Wagon> train = null;
			if (r.nextBoolean()) {
				train = new Train<>(String.format("%03d# Passanger Train", nTrains));
				for (int nWagons = 0; nWagons < r.nextInt(15); nWagons++) {
					PassangerWagon wagon = new PassangerWagon();
					for (int nPassangers = 0; nPassangers < r.nextInt(110); nPassangers++) {
						Passenger passenger = new Passenger(
								String.format("t: %d, w: %d, p: %d", nTrains, nWagons, nPassangers));
						wagon.addCarriable(passenger);
					}
					train.addWagon(wagon);
				}
			} else {
				train = new Train<>(String.format("%03d# Cargo Train", nTrains));
				for (int nWagons = 0; nWagons < r.nextInt(15); nWagons++) {
					CargoItem refCargoItem = new CargoItem("Coal", r.nextInt(50));
					CargoWagon wagon = new CargoWagon(refCargoItem);
					for (int nCargo = 0; nCargo < r.nextInt(110); nCargo++) {
						CargoItem cargoItem = new CargoItem(
								String.format("t: %d, w: %d, c: %d", nTrains, nWagons, nCargo),
								refCargoItem.getProfit());
						wagon.addCarriable(cargoItem);
					}
					train.addWagon(wagon);
				}
			}
			trains.add(train);
		}

		// Add trains to the station
		for (Train<Wagon> t : trains) {
			trainStation.receiveTrain(t);
		}

		// Out
		System.out.println("##################################");
		System.out.println("Trains after receiving:");
		System.out.println("##################################");
		System.out.println("Trains in station sorted by name: ");
		System.out.println("##################################");
		ScreenPrinter.printStrings(trainStation.getTrainsSummariesSortedByName());
		System.out.println("####################################");
		System.out.println("Trains in station sorted by profit: ");
		System.out.println("####################################");
		ScreenPrinter.printStrings(trainStation.getTrainsSummariesSortedByProfit());

		// Remove trains from the station
		for (Train<Wagon> t : trains) {
			if (r.nextBoolean()) {
			trainStation.departTrain(t);
			}
		}

		// Out
		System.out.println("##################################");
		System.out.println("Trains after departure:");
		System.out.println("##################################");
		System.out.println("Trains in station sorted by name: ");
		System.out.println("##################################");
		ScreenPrinter.printStrings(trainStation.getTrainsSummariesSortedByName());
		System.out.println("####################################");
		System.out.println("Trains in station sorted by profit: ");
		System.out.println("####################################");
		ScreenPrinter.printStrings(trainStation.getTrainsSummariesSortedByProfit());
		
		// Trains still in station
		for (Train<Wagon> t : trains) {
			System.out.println(t.getName() + " in station: " + trainStation.isTrainInStation(t));
		}
	}
}
