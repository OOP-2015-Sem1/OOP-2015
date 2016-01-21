package model;

import java.util.ArrayList;
import java.util.HashSet;

import model.train.Train;
import model.train.TrainNameComparator;
import model.train.TrainProfitComparator;

public class TrainStation {
	private final String name;
	private final HashSet<Train> trains;
	
	public TrainStation(String name) {
		this.name = name;
		this.trains = new HashSet<Train>();
	}
	
	public boolean hasTrain(Train train) {
		return trains.contains(train);
	}

	public void receiveTrain(Train newTrain) {
		if (this.hasTrain(newTrain)) {
			System.out.println("Duplicate train in station: " + name);
		} else {
			trains.add(newTrain);
			System.out.println("Received train: " + newTrain.getName());
		}
		
	}
	
	public void departTrain(Train train) {
		if (this.hasTrain(train)) {
			trains.remove(train);
			System.out.println("Train " + train.getName() + " has departed");
		}
	}
	
	public int getTrainNo() {
		return trains.size();
	}
	
	public void listTrainsByName() {
		ArrayList<Train> sortedTrains = new ArrayList<Train>();
		trains.addAll(sortedTrains);
		sortedTrains.sort(new TrainNameComparator());
		
		System.out.println("Trains sorted by name:");
		for (Train t : sortedTrains) {
			System.out.println(t.getSummary());
		}
	}
	
	public void listTrainsByProfit() {
		ArrayList<Train> sortedTrains = new ArrayList<Train>();
		trains.addAll(sortedTrains);
		sortedTrains.sort(new TrainProfitComparator());
		
		System.out.println("Trains sorted by profit:");
		for (Train t : sortedTrains) {
			System.out.println(t.getSummary());
		}
	}
	
	
	
	
}
