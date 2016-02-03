package com.ship.bay.comparators;

import java.util.Comparator;

import com.ship.bay.entities.Ship;
import com.ship.bay.entities.compartments.Compartment;

public class ShipProfitComparator implements
		Comparator<Ship<? extends Compartment>> {

	@Override
	public int compare(Ship<? extends Compartment> ship1,
			Ship<? extends Compartment> ship2) {
		return ship1.getProfit().compareTo(ship2.getProfit());
	}

}
