package main;

import trainItems.*;

public class Main {
	static TrainStation trainStation;

	public static void main(String[] args) {

		trainStation = new TrainStation(5);
		simulateTrains(trainStation);
	}

	private static void simulateTrains(TrainStation trainStation) {
		System.out.println("\n\n TESTING:\n");
		trainStation.getAllTrains();
		trainStation.receiveTrain();
		trainStation.getAllTrains();
		trainStation.departTrain(trainStation.getTrains().get(0));
		trainStation.getAllTrains();
		trainStation.getAllTrainsProfit();
	}

}
