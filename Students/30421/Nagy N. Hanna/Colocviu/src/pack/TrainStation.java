package pack;

import java.util.HashSet;
import java.util.Iterator;

public class TrainStation {

	HashSet<Train> trains= new HashSet<Train>();
	

	
	public void receiveTrain(HashSet<Train> trains, Train train){
		trains.add(train);
	}
	
	public void departingTrain(HashSet<Train> trains,Train train){
		trains.remove(train);
	}
	
	public boolean isInTheStation(HashSet<Train> trains,Train train){
		return trains.contains(train);
	}
	
	
	
	
}
