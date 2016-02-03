package com.airport.comparators;

import java.util.Comparator;

import com.airport.entities.Airplane;
import com.airport.entities.compartments.Compartment;

public class AirplaneNameComparator implements Comparator<Airplane<? extends Compartment>> {

	@Override
	public int compare(Airplane<? extends Compartment> plane1,
			Airplane<? extends Compartment> plane2) {

		return plane1.getName().compareTo(plane2.getName());
	}

}
