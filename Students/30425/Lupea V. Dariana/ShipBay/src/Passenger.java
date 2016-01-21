import java.util.ArrayList;
import java.util.List;

public class Passenger extends Compartment implements Carriable {

	private final static int PASSENGER_COMPART_PRICE = 100;
	private final static int MAX_NO_OF_PASSENGERS = 100;

	List<Carriable> carriablePass = new ArrayList<Carriable>();

	public Passenger() {

	}

	public int calculatePassengerProfit(ArrayList<Carriable> carriable) {
		int passengerProfit = PASSENGER_COMPART_PRICE * (carriable.size() - 1);
		return passengerProfit;
	}

	public void addPassenger(ArrayList<Carriable> passenger) {
		if (carriablePass.size() > MAX_NO_OF_PASSENGERS) {
			System.out.println("The maximum number of passengers is excedeed");
		} else
			carriablePass.add((Carriable) passenger);
	}

	public static int getPassengerCompartPrice() {
		return PASSENGER_COMPART_PRICE;
	}

}
