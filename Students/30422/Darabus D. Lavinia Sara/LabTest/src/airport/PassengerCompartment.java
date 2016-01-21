package airport;

public class PassengerCompartment extends Compartment {

	@Override
	public void computeProfit() {
		// TODO Auto-generated method stub
		this.setProfit(PASSENGER_PRICE * this.compartmentList.size());
	}
	
	

}
