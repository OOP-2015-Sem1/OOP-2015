package com.ship.bay.entities.compartments;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ship.bay.entities.carriables.Carriable;

public abstract class Compartment {

	private final String ID;
	protected List<Carriable> items;

	public Compartment() {
		this.ID = UUID.randomUUID().toString();
		this.items = new ArrayList<Carriable>();
	}

	public void addCarriable(Carriable c) {
		items.add(c);
	}

	public abstract int getProfit();

	public String toString() {
		return String.format("Compartment %s with profit %d\n", this.ID,
				this.getProfit());
	}
}
