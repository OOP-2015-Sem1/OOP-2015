import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TrainStation {

	private Set<Train> trains;
	
	TrainStation() {
		trains = new HashSet<>();
	}
	
	public void addTrain(Train train) {
		trains.add(train);
	} 
	
	public void departeTrain(Train train) {
		trains.remove(train);
	}
	
	public boolean checkTrain(Train train) {
		return trains.contains(train);
	} 
	
	public LinkedList<String> getTrainsSortedByName() {		
		LinkedList<String> names = new LinkedList<>();
		
		for(Train t : trains) {
			names.add(t.toString());
		}
		
		names.sort(null);
		
		return names;
	}
	
	public LinkedList<Integer> getTrainSortedByProfit() {
		LinkedList<Integer> sorted = new LinkedList<>();
		
		for(Train t : trains) {
			sorted.add(t.getProfit());
		}
		
		sorted.sort(null);
		
		return sorted;
	}
}
