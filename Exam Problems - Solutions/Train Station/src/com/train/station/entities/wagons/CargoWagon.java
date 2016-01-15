package com.train.station.entities.wagons;

import com.train.station.entities.carriables.CargoItem;


public class CargoWagon extends Wagon {

	private CargoItem item;

	public CargoWagon(CargoItem item) {
		this.item = item;
	}

	@Override
	public int getProfit() {
		return item.getProfit() * items.size();
	}

}
