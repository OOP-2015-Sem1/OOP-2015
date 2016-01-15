package com.ship.bay.entities.compartments;

import com.ship.bay.entities.carriables.CargoItem;


public class CargoCompartment extends Compartment {

	private CargoItem item;

	public CargoCompartment(CargoItem item) {
		this.item = item;
	}

	@Override
	public int getProfit() {
		return item.getProfit() * items.size();
	}

}
