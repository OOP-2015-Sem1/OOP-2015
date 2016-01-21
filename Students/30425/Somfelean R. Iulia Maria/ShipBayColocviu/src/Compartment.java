import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public abstract class Compartment {

	static final int MAX_NR_OF_PASSENGERS = 100;
	static final int PASSENGER_TICKET_PRICE = 100;
	final String ID;

	private ArrayList<Carriable> carriables = new ArrayList<Carriable>();
	private int profit;
	private boolean isCargoType;
	private boolean isPassengerType;
	// private CargoItem cargoItem;

	public Compartment(boolean isCArgoType, boolean isPassengerType) {

		this.ID = UUID.randomUUID().toString();
		if (isCargoType == !isPassengerType) {
			this.isCargoType = isCargoType;
			this.isPassengerType = isPassengerType;
		} else {
			this.isCargoType = isCargoType;
			this.isPassengerType = !isCargoType;
		}

	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public ArrayList<Carriable> getCarriables() {
		return carriables;
	}

	public void setCarriables(ArrayList<Carriable> carriables) {
		this.carriables = carriables;
	}

	public abstract void addCarriable(Carriable c);;
}
