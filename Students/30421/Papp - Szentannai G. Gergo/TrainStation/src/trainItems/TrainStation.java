package trainItems;

import java.util.ArrayList;

/**
 * 
 * @author gergo_000
 *
 */
public class TrainStation {
	// TODO: add variable number of UNIQUE trains
	private ArrayList<Train> trains;

	public TrainStation() {
		System.out.println("Starting train station...");
		setTrains(new ArrayList<Train>());
	}

	public TrainStation(int nrOfInitialTrains) {
		System.out.println("Starting train station...");
		setTrains(new ArrayList<Train>());
		for (int i = 0; i < nrOfInitialTrains; i++) {
			receiveTrain();
		}
		getAllTrains();
	}

	public void receiveTrain() {
		System.out.println("Preparing to recieve train...");

		getTrains().add(new Train("TrainWithRandomName" + getTrains().size()));
	}

	public void departTrain(Train train) {
		System.out.println("Departing train...");
		if (checkIfTrainInStation(train)) {
			getTrains().remove(train);
		} else
			System.out.println("Train not found! Could not depart train!");
	}

	public boolean checkIfTrainInStation(Train train) {
		System.out.println("Checking if train is in station...");
		if (getTrains().contains(train)) {
			return true;
		} else
			return false;
	}

	public int getAllTrainsProfit() {
		int totalProfit = 0;
		System.out.println("Printing all trains and profit...");
		for (int i = 0; i < getTrains().size(); i++) {
			System.out.print(getTrains().get(i).getName() + "; Profit: ");
			totalProfit += getTrains().get(i).getProfit();
			System.out.println(getTrains().get(i).getProfit());
		}
		return totalProfit;
	}

	public void getAllTrains() {
		System.out.println("Printing all trains and wagon nr...");
		for (int i = 0; i < getTrains().size(); i++) {
			System.out.print(getTrains().get(i).getName());
			System.out.println("; nr of wagons: " + getTrains().get(i).getNrOfWagons());
		}
	}

	public ArrayList<Train> getTrains() {
		return trains;
	}

	public void setTrains(ArrayList<Train> trains) {
		this.trains = trains;
	}
}
