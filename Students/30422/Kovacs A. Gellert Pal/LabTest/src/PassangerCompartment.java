
public class PassangerCompartment extends Compartment {
	private final int MAX_PASSENGER_NUMBER = 100; 
	private final int TICKET_PRICE = 100;
	
	@Override
	public int getProfit() {
		return carry.size()*TICKET_PRICE;
	}
	
	public void addPassenger(Passenger passenger) {
		if (carry.size() < MAX_PASSENGER_NUMBER) {
			carry.add(passenger);
		}
		else {
			System.out.println("This compartment is full.");
		}
	}
}
