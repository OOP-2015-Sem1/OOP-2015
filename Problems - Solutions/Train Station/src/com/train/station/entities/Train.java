package com.train.station.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import com.train.station.entities.wagons.Wagon;

public class Train<T extends Wagon> {
	private Set<T> wagons;
	private String name;

	public Train(String name) {
		wagons = new LinkedHashSet<T>();
		this.name = name;
	}

	public void addWagon(T wagon) {
		wagons.add(wagon);
	}

	public void removeWagon(T wagon) {
		wagons.remove(wagon);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProfit() {
		int profit = 0;
		for (T wagon : wagons) {
			profit += wagon.getProfit();
		}
		return profit;
	}

	public String toString() {
		return String.format("Train %s with %d wagons and %d profit",
				this.name, this.wagons.size(), this.getProfit());
	}
}
