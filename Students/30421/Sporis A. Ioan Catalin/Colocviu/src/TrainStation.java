import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TrainStation {
	private HashSet<Train> trains = new HashSet<Train>();

	public void receiveTrain(Train train) {
		this.trains.add(train);
	}

	public void departTrain(Train train) {
		if (this.checkTrain(train)) {
			this.trains.remove(train);
		}
	}

	public boolean checkTrain(Train train) {
		return this.trains.contains(train);

	}

	public void addTrains() {
		for (int i = 0; i < 10; i++) {
			this.trains.add(new Train());
		}
		for (int i = 0; i < 10; i++) {
			this.trains.add(new Train());
		}

	}

	public HashSet<Train> getTrains() {
		return this.trains;
	}

	public void getSummary() {
		for (Train train : this.trains) {
			System.out.println("Train Name: " + train.getName() + "Number of Wagons: " + " " + train.wagons.size() + " "
					+ "Profit: " + train.getProfit());
		}
	}
}
