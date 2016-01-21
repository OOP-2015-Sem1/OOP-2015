package com.pack.airport;

import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

public class Plane {
	private LinkedList<Compartment> compartments;
	private final String name;
	private int profit = 0;

	public Plane() {
		name = UUID.randomUUID().toString();
		Random r = new Random();
		int type = Math.abs(r.nextInt(2));
		int nrOfInitialCompartments = Math.abs(r.nextInt(100));
		compartments = new LinkedList<Compartment>();
		for (int i = 0; i < nrOfInitialCompartments; i++) {
			if (type == 0) {
				compartments.add(new CargoComp());
			} else {
				compartments.add(new PassengerComp());
			}

		}
	}

	private int computeProfit() {
		int profit = 0;
		for (int i = 0; i < compartments.size(); i++) {
			profit += compartments.get(i).getProfit();
		}
		return profit;
	}

	public int getNrOfComps(){
		return compartments.size();
	}
	
	public String getName() {
		return name;
	}

	public int getProfit() {
		profit = computeProfit();
		return profit;
	}

}
