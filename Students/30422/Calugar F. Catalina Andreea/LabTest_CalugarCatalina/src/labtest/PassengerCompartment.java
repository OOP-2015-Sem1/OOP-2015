package labtest;

public class PassengerCompartment extends Compartment {

	private static final int PASS_PRICE = 100;
	private static final int MAX_PASSANGER_SIZE = 100;

	public int computeProfitPassenger() {
		int passengerProfit;
		if (getCarriable().size() <= MAX_PASSANGER_SIZE) {
			passengerProfit = getCarriable().size() * PASS_PRICE;
		} else {
			System.out.println("To many Pasangers!");
			return 0;
		}
		return passengerProfit;
	}
}
