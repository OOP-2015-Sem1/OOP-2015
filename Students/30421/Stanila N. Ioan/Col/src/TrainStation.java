import java.util.HashSet;

public class TrainStation {

	private HashSet<Train> trains;
	
	public TrainStation() {
		trains = new HashSet<Train>();
	}
	
	public void receiveTrain(Train train) {
		trains.add(train);
	}
	
	public void departTrain(Train train) {
		trains.remove(train);
	}
	
	public boolean isInStation(Train train) {
		if (trains.contains(train)) {
			return true;
		}
		return false;
	}
	
	public void sortByName() {
		
	}
	
	public void sortByProfit() {
		
	}
	
	public void getTrainInfo(Train train) {
		System.out.println(train.toString());
	}
}
