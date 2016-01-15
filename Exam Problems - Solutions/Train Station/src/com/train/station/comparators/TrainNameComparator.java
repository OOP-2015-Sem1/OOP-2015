package com.train.station.comparators;

import java.util.Comparator;

import com.train.station.entities.Train;
import com.train.station.entities.wagons.Wagon;

public class TrainNameComparator implements Comparator<Train<? extends Wagon>> {

	@Override
	public int compare(Train<? extends Wagon> train1,
			Train<? extends Wagon> train2) {

		return train1.getName().compareTo(train2.getName());
	}

}
