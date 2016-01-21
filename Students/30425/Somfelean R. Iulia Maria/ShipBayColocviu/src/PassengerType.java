
public class PassengerType extends Compartment {

	public PassengerType() {
		super(true, false);
	}

	private void computeProfit() {

		int profit = PASSENGER_TICKET_PRICE * getCarriables().size();
		setProfit(profit);
	}

	@Override
	public void addCarriable(Carriable c) {
		// TODO Auto-generated method stub
		if(getCarriables().size() < 99){
			getCarriables().add(c);
		}
		else
			System.out.println("Passengers limit exceeded");
	}

}
