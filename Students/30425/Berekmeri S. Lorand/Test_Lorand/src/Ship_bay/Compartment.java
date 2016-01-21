package Ship_bay;

import java.util.UUID;

public class Compartment {

	private final static int fixedprice = 100;
	private final static int nrmaxofpassengers = 100;
	private String ID = UUID.randomUUID().toString();
	int price;

	public Passenger setPassenger() {
		Passenger passenger = new Passenger();
		return passenger;
	}

	public CargoItem CargoItem() {
		CargoItem cargo = new CargoItem();
		return cargo;
	}

	public int Profit(Object carriable, Object cargo) {
		int profit = 0;
		return profit;
	}

	public void setID() {
		this.ID = UUID.randomUUID().toString();
	}

	public String getID() {
		return this.ID();
	}

	private String ID() {
		return null;
	}
}
