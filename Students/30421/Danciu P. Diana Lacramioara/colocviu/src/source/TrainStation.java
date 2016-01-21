package source;

import java.util.*;

public class TrainStation {

	public final int NOT_IN_STATION = -1;
	public final int IN_STATION = 1;

	public List<Train> trains = new ArrayList<Train>();

	public void receiveTrain(Train train) {
		if (train != null) {
			trains.add(train);
		}
	}

	public void departTrain(Train train) {
		if (train != null) {
			for (Train t : trains) {
				if (train == t) {
					trains.remove(train);
				}
			}
		}
	}

	public int trainInStation(Train train) {
		if (train != null) {
			if (trains.contains(train)) {
				return (IN_STATION);
			} else {
				return (NOT_IN_STATION);
			}
		}
		return 0;
	}

}
