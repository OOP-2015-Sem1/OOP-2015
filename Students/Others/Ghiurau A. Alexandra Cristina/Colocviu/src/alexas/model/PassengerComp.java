package alexas.model;

public class PassengerComp extends Compartment<Passenger> {

	private final int DEFAULT_PRICE = 100;

	@Override
	protected int getProfit() {
		int totalProfit = 0;
		for(Carriable p : list){
			totalProfit = list.size() * DEFAULT_PRICE;
		}
		return totalProfit;
	}

}
