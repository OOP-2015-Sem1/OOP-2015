package com.train.station.entities.wagons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.train.station.entities.carriables.Carriable;

public abstract class Wagon {

	private final String ID;
	protected List<Carriable> items;

	public Wagon() {
		this.ID = UUID.randomUUID().toString();
		this.items = new ArrayList<Carriable>();
	}

	public void addCarriable(Carriable c) {
		items.add(c);
	}

	public abstract int getProfit();

	public String toString() {
		return String.format("Wagon %s with profit %d\n", this.ID,
				this.getProfit());
	}
}
