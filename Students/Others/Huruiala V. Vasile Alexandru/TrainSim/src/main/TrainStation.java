package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

import model.train.Train;
import model.wagon.Wagon;

public class TrainStation {
	private LinkedList<Train<Wagon>> trainsInStation;

	public TrainStation() {
		trainsInStation = new LinkedList<>();
	}

	public boolean receiveTrain(Train train) {
		return trainsInStation.add(train);
	}

	public boolean departTrain(Train train) {
		return trainsInStation.remove(train);
	}

	public boolean isTrainInStation(Train train) {
		return trainsInStation.contains(train);
	}

	public static ArrayList<String> getSummaries(Collection<Train> trains) {
		ArrayList<String> trainsSummaries = new ArrayList<>();
		for (Train<Wagon> train : trains) {
			trainsSummaries.add(train.getSummary());
		}

		return trainsSummaries;
	}

	public ArrayList<String> getTrainsSummariesSortedByName() {
		LinkedList<Train> sortedTrains = new LinkedList<>(trainsInStation);
		sortedTrains.sort(new TrainNameComparator());
		return getSummaries(sortedTrains);
	}

	public ArrayList<String> getTrainsSummariesSortedByProfit() {
		LinkedList<Train> sortedTrains = new LinkedList<>(trainsInStation);
		sortedTrains.sort(new TrainProfitComparator());
		return getSummaries(sortedTrains);
	}

	private class TrainNameComparator implements Comparator<Train> {

		@Override
		public int compare(Train arg0, Train arg1) {
			String name0 = arg0.getName();
			String name1 = arg1.getName();

			return name0.compareTo(name1);
		}
	}

	private class TrainProfitComparator implements Comparator<Train> {

		@Override
		public int compare(Train arg0, Train arg1) {
			int profit0 = arg0.getProfit();
			int profit1 = arg1.getProfit();

			return -Integer.compare(profit0, profit1);
		}
	}
}
