package train;

import java.util.Set;

public class TrainStation {
	Set<Train> Station = new Set<Train>();
	

	public void TrainEntersStation(Train train) {
		Station.add(train);
		
	}

	public void TrainLeavesStation(Train train) {
		Station.remove(train);
	}

	public boolean TrainInStation(Train train) {
		for (Object Train : Station) {
			if (Train == train)
				return true;
			else
				return false;

		}
	}
}
