package com.pack.airport;

import java.util.ArrayList;
import java.util.Random;

import com.pack.carriable.Carriable;

public abstract class Compartment {
	public final String UUID;
	protected ArrayList<Carriable> objects;

	public Compartment() {
		UUID = java.util.UUID.randomUUID().toString();
		objects = new ArrayList<Carriable>();
		Random r = new Random();
		int nrOfInitialObjects = Math.abs(r.nextInt(100));
		for (int i = 0; i < nrOfInitialObjects; i++) {
			addObject();
		}
	}

	public abstract void addObject();

	protected abstract int computeProfit();

	public int getProfit() {
		return computeProfit();
	}
}
