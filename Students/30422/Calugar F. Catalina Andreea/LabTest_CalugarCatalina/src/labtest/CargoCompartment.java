package labtest;

import static labtest.CargoItem.profit;

public class CargoCompartment extends Compartment {

	public int computeProfitCargo() {
		int cargoProfit = getCarriable().size() * profit;
		return cargoProfit;
	}
}
