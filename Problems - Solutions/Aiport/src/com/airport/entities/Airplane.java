package com.airport.entities;

import java.util.LinkedHashSet;
import java.util.Set;

import com.airport.entities.compartments.Compartment;

public class Airplane<T extends Compartment> {
	private Set<T> compartments;
	private String name;

	public Airplane(String name) {
		compartments = new LinkedHashSet<T>();
		this.name = name;
	}

	public void addCompartment(T compartment) {
		compartments.add(compartment);
	}

	public void removeCompartment(T compartment) {
		compartments.remove(compartment);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProfit() {
		int profit = 0;
		for (T compartment : compartments) {
			profit += compartment.getProfit();
		}
		return profit;
	}

	public String toString() {
		return String.format("Airplane %s with %d compartments and %d profit",
				this.name, this.compartments.size(), this.getProfit());
	}
}
