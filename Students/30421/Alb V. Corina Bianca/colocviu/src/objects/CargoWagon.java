package objects;

import java.util.UUID;

import interfaces.Wagon;

public class CargoWagon implements Wagon{ 
	
	private CargoItem item;
	private int profit;
	private final String ID = UUID.randomUUID().toString();
	private int nrOfItems;
		
	public CargoItem getItem() {
		return item;
	}

	public void setItem(CargoItem item) {
		this.item = item;
	}

	@Override
	public int getWagonProfit() {
		// TODO Auto-generated method stub
		return profit;
	}

	@Override
	public int getNrOfCariable() {
		// TODO Auto-generated method stub
		return nrOfItems;
	}

	@Override
	public void setNrOfCariable(int nrOfCariable) {
		this.nrOfItems = nrOfCariable;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setWagonProfit() {
		profit = getNrOfCariable() * getWagonProfit();		
	}
}