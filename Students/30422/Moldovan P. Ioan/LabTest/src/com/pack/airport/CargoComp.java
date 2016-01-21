package com.pack.airport;

import com.pack.carriable.CargoItem;
import com.pack.carriable.Carriable;

public class CargoComp extends Compartment {
	private CargoItem carriedItem;

	public CargoComp() {
		carriedItem = new CargoItem();
	}

	public void addObject() {
		objects.add(new Carriable() {
		});
	}

	protected int computeProfit() {
		return (objects.size() * carriedItem.getProfit());
	}

}
