package trains.components;

import java.util.ArrayList;

public class CargoWagon extends WagonModel{
	
	private int nrOfItems;
	private int profit;
	private CargoItem cargoItem;
	
	
	public CargoWagon() {
		CargoItem cargoItem = new CargoItem();
		nrOfItems = cargoItem.getNrOfItems();
		profit = cargoItem.getProfit();
	}
	
	
	public int computeProfit() {
		return nrOfItems * profit;
	}
	
}
