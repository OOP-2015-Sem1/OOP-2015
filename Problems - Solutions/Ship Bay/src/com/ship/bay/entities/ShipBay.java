package com.ship.bay.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ship.bay.entities.compartments.Compartment;

public class ShipBay {
	private Set<Ship<? extends Compartment>> ships;

	public ShipBay() {
		ships = new HashSet<Ship<? extends Compartment>>();
	}

	public void addShip(Ship<? extends Compartment> ship) {
		ships.add(ship);
	}

	public void removeShip(Ship<? extends Compartment> ship) {
		ships.remove(ship);
	}

	public boolean isShipInBay(Ship<? extends Compartment> ship) {
		return ships.contains(ship);
	}

	public void printSortedShips(
			Comparator<Ship<? extends Compartment>> comparable) {
		List<Ship<? extends Compartment>> sortedShipsList = new ArrayList<Ship<? extends Compartment>>();
		sortedShipsList.addAll(ships);

		Collections.sort(sortedShipsList, comparable);
		System.out.println("Printing sorted ships");
		printShips(sortedShipsList);
	}

	public void shipInfo(Ship<? extends Compartment> ship) {
		if (ships.contains(ship)) {
			System.out.println("The info of the ship:");
			System.out.println(ship);
		} else {
			System.out
					.println("Could not give info in ship - it is not currently in the bay.");
		}
	}

	private void printShips(Iterable<Ship<? extends Compartment>> ships) {
		for (Ship<? extends Compartment> ship : ships) {
			System.out.println(ship);
		}
		System.out.println();
	}

}
