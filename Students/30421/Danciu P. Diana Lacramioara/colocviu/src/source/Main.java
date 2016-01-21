package source;

import java.util.*;

public class Main {

	public static <T> void main(String[] args) {
		// TODO Auto-generated method stub

		TrainStation trainStation = new TrainStation();
		Collection<? extends Train> passengerW = (Collection<? extends Train>) new Passenger();
 		Train train = new Train("1", passengerW);

		trainStation.receiveTrain(train);
		
	}

}
