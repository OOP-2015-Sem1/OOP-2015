import java.util.*;

public class Station {
	private Set<Train> trains = new HashSet<Train>();
	
	public void receive(Train train){
		trains.add(train);
	}
	
	public void depart(Train train){
		trains.remove(train);
	}
	
	public boolean inStation(Train train){
		return trains.contains(train);
	}
	
	public Set<Train> getTrains(){
		return trains;
	}
}
