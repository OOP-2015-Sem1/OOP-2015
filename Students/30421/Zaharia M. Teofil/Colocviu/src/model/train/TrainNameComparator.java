package model.train;

import java.util.Comparator;

public class TrainNameComparator implements Comparator<Train> {

	public TrainNameComparator() {
		
	}
	
	@Override
	public int compare(Train o1, Train o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
