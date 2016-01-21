package model.train;

import java.util.Comparator;

public class TrainProfitComparator implements Comparator<Train> {
	
	public TrainProfitComparator() {
		
	}
	
	@Override
	public int compare(Train o1, Train o2) {
		
		return o1.getProfit() - o2.getProfit();
	}

}
