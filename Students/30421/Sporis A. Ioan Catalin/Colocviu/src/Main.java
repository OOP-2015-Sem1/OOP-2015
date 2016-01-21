import java.util.Random;

public class Main {

	public static void main(String[] args) {
		TrainStation trainStation = new TrainStation();
		trainStation.addTrains();
		for(Train train: trainStation.getTrains()){
			System.out.println(train);
		}
		
		
		
		
	}

}
