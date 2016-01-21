package pac1;

import java.util.UUID;

public class Compartment {
	private final String ID;
	private final int TICKET_PRICE = 100;
	private final int MAX_PASSENGERS = 100;
	public boolean type; // if type = 1 than passenger type
	public Carriable[] Objects = new Carriable[MAX_PASSENGERS];
	public int carriables = 0;

	Compartment(boolean type) {
		this.ID = UUID.randomUUID().toString();
		this.type = type;
	}

	public void addCarriable(String name) {
		if (carriables < MAX_PASSENGERS) {
			Objects[carriables] = new Passenger(name);
			carriables++;
		}else{
			System.out.println("Too many passangers in this Compartment");
		}
	}

	public void addCarriable(String name, int profit) {
		Objects[0] = new CargoItem(name, profit);
		carriables++;
	}

	public int computeProfits() {
		int profit = 0;

		if (type) {
			profit = Objects.length * TICKET_PRICE;
		} else {
			profit = Objects.length * Objects[0].getProfit();
		}

		return profit;
	}
}
