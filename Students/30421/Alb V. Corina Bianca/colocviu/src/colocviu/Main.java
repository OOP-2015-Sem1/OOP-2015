package colocviu;

import java.awt.List;
import java.util.ArrayList;
import java.util.UUID;

import interfaces.Wagon;
import objects.CargoWagon;
import objects.PassengerWagon;
import objects.Train;

public class Main {
	
	private static ArrayList<Train> trainList = new ArrayList<>();  

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		populateStation();
	}

	private static void populateStation() {
		for (int i = 0; i < Constants.NR_OF_TRAINS_IN_STATION; i++) {	
			trainList.add(i, createNewTrain(new Train()));
		}
	}
	
	private static Train createNewTrain(Train train) {
		
		train.setNrOfWagons(UUID.randomUUID().hashCode());
		
		if (UUID.randomUUID().hashCode() % 2 == 1) {
			train.setPassenger(true);
//			train.setCargo(false);
		} else {
			train.setPassenger(false);
//			train.setCargo(true);
		}
		
		
		
		for (int i = 0; i<train.getNrOfWagons(); i++) {
			Wagon wagon;
			if (train.isPassenger()) {
				wagon = new PassengerWagon();
				wagon.setNrOfCariable(UUID.randomUUID().hashCode());
			} else {
				wagon = new CargoWagon();
				wagon.setNrOfCariable(UUID.randomUUID().hashCode());
			}
			
			train.addToList(wagon);
		}
	
		
		return train;
	}
}
