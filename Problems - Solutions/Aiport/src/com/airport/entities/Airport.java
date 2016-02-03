package com.airport.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.airport.entities.compartments.Compartment;

public class Airport {
	private Set<Airplane<? extends Compartment>> airplanes;

	public Airport() {
		airplanes = new HashSet<Airplane<? extends Compartment>>();
	}

	public void addAirplane(Airplane<? extends Compartment> airplane) {
		airplanes.add(airplane);
	}

	public void removeAirplane(Airplane<? extends Compartment> airplane) {
		airplanes.remove(airplane);
	}

	public boolean isAirplaneInAirport(Airplane<? extends Compartment> airplane) {
		return airplanes.contains(airplane);
	}

	public void printSortedAirplanes(
			Comparator<Airplane<? extends Compartment>> comparable) {
		List<Airplane<? extends Compartment>> sortedAirplanesList = new ArrayList<Airplane<? extends Compartment>>();
		sortedAirplanesList.addAll(airplanes);

		Collections.sort(sortedAirplanesList, comparable);
		System.out.println("Printing sorted airplanes");
		printPlanes(sortedAirplanesList);
	}

	public void airplaneInfo(Airplane<? extends Compartment> airplane) {
		if (airplanes.contains(airplane)) {
			System.out.println("The info of the airplane:");
			System.out.println(airplane);
		} else {
			System.out
					.println("Could not give info in airplane - it is not currently in the airport.");
		}
	}

	private void printPlanes(Iterable<Airplane<? extends Compartment>> airplanes) {
		for (Airplane<? extends Compartment> airplane : airplanes) {
			System.out.println(airplane);
		}
		System.out.println();
	}

}
