package airport;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Compartment {

	protected static final int PASSENGER_PRICE = 100;
	protected static final int MAX_NUMBER_PASSENGERS = 100;
	public final String ID;
	private int profit;
	protected ArrayList<? extends Carriable> compartmentList = new ArrayList();

	Compartment() {
		this.ID = this.generateID();
	}

	public String generateID() {
		String string = UUID.randomUUID().toString();
		return string;
	}

	public abstract void computeProfit();

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

}
