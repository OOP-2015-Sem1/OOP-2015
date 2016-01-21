import java.util.ArrayList;

public class PersonCompartiment extends Compartment {
	private static int price;
	private int max;
	private ArrayList<Passenger> passengers;

	public PersonCompartiment(String ID, int profit) {
		super(ID, profit);
		passengers = new ArrayList<Passenger>();
		price = 100;
		max = 100;
	}

	@Override
	public void computeProfit() {
		super.profit = price * passengers.size();

	}

	public void addPassenger(Passenger p) {
		if (passengers.size() >= 100) {
			System.out.println("Too many passengers");
		} else {
			passengers.add(p);
		}

	}

}
