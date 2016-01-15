package com.airport.comparators;

import java.util.Comparator;

import com.airport.entities.Airplane;
import com.airport.entities.compartments.Compartment;

public class AirplaneProfitComparator implements
		Comparator<Airplane<? extends Compartment>> {

	@Override
	public int compare(Airplane<? extends Compartment> plane1,
			Airplane<? extends Compartment> plane2) {
		return plane1.getProfit().compareTo(plane2.getProfit());
	}

}
