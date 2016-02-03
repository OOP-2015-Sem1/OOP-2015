package com.train.station.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.train.station.entities.wagons.Wagon;

public class TrainStation {
	private Set<Train<? extends Wagon>> trains;

	public TrainStation() {
		trains = new HashSet<Train<? extends Wagon>>();
	}

	public void addTrain(Train<? extends Wagon> train) {
		trains.add(train);
	}

	public void removeTrain(Train<? extends Wagon> train) {
		trains.remove(train);
	}

	public boolean isTrainInStation(Train<? extends Wagon> train) {
		return trains.contains(train);
	}

	public void printSortedTrains(Comparator<Train<? extends Wagon>> comparable) {
		List<Train<? extends Wagon>> sortedTrainsList = new ArrayList<Train<? extends Wagon>>();
		sortedTrainsList.addAll(trains);

		Collections.sort(sortedTrainsList, comparable);
		System.out.println("Printing sorted trains");
		printTrains(sortedTrainsList);
	}

	public void trainInfo(Train<? extends Wagon> train) {
		if (trains.contains(train)) {
			System.out.println("The info of the train:");
			System.out.println(train);
		} else {
			System.out
					.println("Could not give info in train - it is not currently in the train station.");
		}
	}

	private void printTrains(Iterable<Train<? extends Wagon>> trains) {
		for (Train<? extends Wagon> train : trains) {
			System.out.println(train);
		}
		System.out.println();
	}

}
