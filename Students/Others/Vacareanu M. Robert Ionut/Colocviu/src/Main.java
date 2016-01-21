import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Main {

	static HashSet<Train<Passenger>> passengerTrains = new HashSet<>();
	static HashSet<Train<CargoItem>> cargoTrains = new HashSet<>();

	static Train<Passenger> passengerTrain1 = new Train<>("XA");
	static Train<Passenger> passengerTrain2 = new Train<>();
	static Train<CargoItem> cargoTrain1 = new Train<>();
	static Train<CargoItem> cargoTrain2 = new Train<>();
	
	static Wagon<CargoItem> cargoWagon1 = new Wagon<>();//, cargoWagon2, cargoWagon3;
	static Wagon<Passenger> passengerWagon1 = new Wagon<>();
	static Wagon<Passenger> passengerWagon2 = new Wagon<>();
	static Passenger passenger = new Passenger("TEST");
	
	static CargoItem cargo = new CargoItem("Salt", 100);
	
	static HashMap<Train<Passenger>, Integer> passengerTrainProfit = new HashMap<>();
	static HashMap<Train<CargoItem>, Integer> carriableProfit = new HashMap<>();

	static TrainStation trainStation = new TrainStation();

	Set<Integer> test;
	Main(){

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		passengerWagon1.setLimit(Carriable.LIMIT);
		for(int i=0; i<1000; i++) {
			passengerWagon1.addCarriable(passenger);
		}
		
		passengerTrain1.addWagon(passengerWagon1);
		
		
		
		passengerTrains.add(passengerTrain1);
		passengerTrains.add(passengerTrain1);
		System.out.println(passengerWagon1.getCarriable().size());
		System.out.println(passengerTrain1.getProfit());
		
		passengerWagon2.setLimit(Carriable.LIMIT);
		for(int i=0; i<10; i++) {
			passengerWagon2.addCarriable(passenger);
		}
		
		passengerTrain2.addWagon(passengerWagon2);
		
		
		
		passengerTrains.add(passengerTrain2);

		
		
		cargoWagon1.addCarriable(new CargoItem("Salt", 100));
		cargoWagon1.addCarriable(new CargoItem("Salt", 100));
		cargoWagon1.addCarriable(new CargoItem("Salt", 100));
		cargoWagon1.addCarriable(new CargoItem("Salt", 100));
		cargoWagon1.addCarriable(new CargoItem("Salt", 100));
		cargoWagon1.addCarriable(new CargoItem("Salt", 100));

		cargoTrain1.addWagon(cargoWagon1);
		cargoTrains.add(cargoTrain1);
		System.out.println(cargoWagon1.getCarriable().size());
		System.out.println(cargoTrain1.getProfit());
		
		
		
		
		
		passengerTrainProfit.put(passengerTrain1, passengerTrain1.getProfit());
		passengerTrainProfit.put(passengerTrain2, passengerTrain2.getProfit());

		List<String> trainsName = new LinkedList<>();
		for(Train t : passengerTrainProfit.keySet()) {
			trainsName.add(t.getName());
		}
		trainsName.sort(null);
		
		System.out.println(passengerTrain1.getSummary());
		
		
		trainStation.addTrain(passengerTrain1);
		System.out.println(trainStation.checkTrain(passengerTrain1));
		System.out.println(trainStation.getTrainSortedByProfit().toString());
		trainStation.departeTrain(passengerTrain1);
		System.out.println(trainStation.checkTrain(passengerTrain1));
		
	}

}