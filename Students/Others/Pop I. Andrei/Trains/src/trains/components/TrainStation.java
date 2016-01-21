package trains.components;

import java.util.LinkedHashSet;
import java.util.UUID;

public class TrainStation {
	
	private int nrOfTrains;
	private LinkedHashSet<Train> trains;
	
	public TrainStation() {
		nrOfTrains = 0;
		trains = new LinkedHashSet<Train>();
	}
	
	public void receive() {
		Train train = new Train();
		trains.add(train);
	}
	
	public void depart(Train depTrain) {
		
		int i = 0;
		
		for(Train train : trains) {
			if(train.equals(depTrain)) {
				trains.remove(i);
			}
			i++;
		}
	}
	
	public boolean checkIfTrainInTheStation(Train theTrain) {
		
		for(Train train : trains) {
			if(train.equals(theTrain)) {
				return true;
			}
		}
		return false;
		
	}
	
	
	public void test() {
		UUID.randomUUID().toString();
	}
	
}
