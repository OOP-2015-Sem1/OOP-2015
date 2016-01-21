package model.wagon;

import model.carriable.CargoItem;
import model.carriable.Carriable;

public class CargoWagon extends Wagon {

	private CargoItem type;
	
	public CargoWagon(String id, CargoItem type) {
		super(id);
		this.type = type;
	}

	@Override
	public int getProfit() {
		return getCarrySize() * type.getProfit();
	}
	
	public void addCargoItem(CargoItem newCargo) {
		carry.add((Carriable) newCargo);
	}
	
	public CargoItem getCargoType() {
		return type.copy();
	}

}
