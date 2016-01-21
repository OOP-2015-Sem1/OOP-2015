package MainClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

import Classes.CargoItem;
import Classes.Passenger;
import Classes.Train;
import Classes.TrainStation;
import Classes.Wagon;

public class Main {
	public static void main(String[] args) {
		
		Passenger pass1 = new Passenger("Mihai");
		Passenger pass2 = new Passenger("Alin");
		Passenger pass3 = new Passenger("Mihaela");
		Passenger pass4 = new Passenger("Radu");
		Passenger pass5 = new Passenger("Alina");
		ArrayList<Passenger> wagonPassengerItems = new ArrayList<Passenger>();
		wagonPassengerItems.add(pass1);
		wagonPassengerItems.add(pass2);
		wagonPassengerItems.add(pass3);
		wagonPassengerItems.add(pass4);
		wagonPassengerItems.add(pass5);
		Wagon wagonPassengers1 = new Wagon(wagonPassengerItems, "Passenger");
		Wagon wagonPassengers2 = new Wagon(wagonPassengerItems, "Passenger");
		Wagon wagonPassengers3 = new Wagon(wagonPassengerItems, "Passenger");

		LinkedList<Wagon> passengerWagons = new LinkedList<Wagon>();
		passengerWagons.add(wagonPassengers1);
		passengerWagons.add(wagonPassengers2);
		passengerWagons.add(wagonPassengers3);

		Train train1 = new Train("Accelerat", passengerWagons);
		Train train2 = new Train("Accelerat2", passengerWagons);
		Train train3 = new Train("Accelerat3", passengerWagons);

		TrainStation trainStation = new TrainStation();
		trainStation.addNewTrain(train2);
		trainStation.addNewTrain(train3);
		trainStation.addNewTrain(train1);

		
		System.out.println("Train " + train1.getName() + " is in the station: " + trainStation.checkTrainInStation(train1));
		System.out.println("Train details: ");
		train1.getTrainDetails();
		trainStation.trainsSortedByName();
		trainStation.trainsSortedByProfit();

		CargoItem cargoItem1 = new CargoItem("CargItem", 22);
		CargoItem cargoItem2 = new CargoItem("CargItem", 22);
		CargoItem cargoItem3 = new CargoItem("CargItem", 22);
		CargoItem cargoItem4 = new CargoItem("CargItem", 22);
		CargoItem cargoItem5 = new CargoItem("CargItem", 22);
		ArrayList<CargoItem> wagonCargoItems = new ArrayList<CargoItem>();
		wagonCargoItems.add(cargoItem1);
		wagonCargoItems.add(cargoItem2);
		wagonCargoItems.add(cargoItem3);
		wagonCargoItems.add(cargoItem4);
		wagonCargoItems.add(cargoItem5);
		Wagon wagonCargo = new Wagon(wagonCargoItems, "Cargo");

	}
	
}
