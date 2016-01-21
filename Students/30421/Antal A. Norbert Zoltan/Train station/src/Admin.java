import java.util.Random;
import java.util.Set;

public class Admin {
	private static Station station;
	
	public static void main(String[] args){
		station = new Station();
		Random rand = new Random();
		int nrTrains = rand.nextInt(50);
		for (int i = 0; i<nrTrains; i++){
			Train<PassengerWagon> passTrain = new Train<PassengerWagon>(" ");
			int nrWagons = rand.nextInt(40);
			for (int j = 0; j<nrWagons; j++){
			passTrain.addWagon(new PassengerWagon(" ", rand.nextInt(70)));
			}
			station.receive(passTrain);
		}
		nrTrains = rand.nextInt(50);
		for (int i = 0; i<nrTrains; i++){
			Train<CargoWagon> cargTrain = new Train<CargoWagon>(" ");
			int nrWagons = rand.nextInt(40);
			for (int j = 0; j<nrWagons; j++){
			cargTrain.addWagon(new CargoWagon(" ", rand.nextInt(70), new CargoItem(" ", 40)));
			}
			station.receive(cargTrain);
		}
	}
	
	public void byName(){
		Set<Train> trains = station.getTrains();
	}
	
	public void byProfit(){
		Set<Train> trains = station.getTrains();
	}
	
	public void getSummary(){
		System.out.println("The trains are: ");
		Set<Train> trains = station.getTrains();
		for (Train train : trains){
			System.out.println(train.name + ", " + train.getNrWag() + ", " + train.computeProfit());
		}
	}
}
