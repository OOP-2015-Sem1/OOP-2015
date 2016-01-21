package trains.components;

import java.util.ArrayList;

public class PassengerWagon extends WagonModel{
	
	private final int  fixedPrice = 100;
	private final int maxPassengers = 100;
	private int nrOfPassengers;
	private ArrayList<Carriable> passengers = new ArrayList<>();
	
	
	public PassengerWagon() {
		nrOfPassengers = 0;
	}
	
	public void addPassenger() {
		if(maxPassengers >= nrOfPassengers) {
			Item pas = new Item();
			pas.setItemProfit(fixedPrice);
			passengers.add(pas);
			System.out.println("The passenger added");
		}
		else {
			System.out.println("The passenger wagon is full");
		}
	}

	@Override
	public int computeProfit() {
		return nrOfPassengers * fixedPrice;
	}
	
	
}
