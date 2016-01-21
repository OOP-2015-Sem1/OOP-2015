package model.wagon;

import model.cargo.CargoItem;

public class CargoWagon extends Wagon {
	private CargoItem referenceCarriable;

	public CargoWagon(CargoItem referenceCarriable) {
		super();
		this.referenceCarriable = referenceCarriable;
	}

	@Override
	public int getProfit() {
		int profit = getCarriables().size() * referenceCarriable.getProfit();
		return profit;
	}
}
