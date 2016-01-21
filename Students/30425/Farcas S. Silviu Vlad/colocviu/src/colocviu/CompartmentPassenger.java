package colocviu;

import java.util.LinkedList;

public class CompartmentPassenger extends Compartment {

	private final int price=100;
	private final int maximum = 100;
	private int profit;
	public void computeProfit(Compartment compartment){
		profit=compartment.getCarriables().size()*price;
	}
	public boolean addPassenger (Compartment compartment, Passenger passenger){
		if(compartment.getCarriables().size()<maximum){
			compartment.getCarriables().add(passenger);
			return true;
		}
		return false;		
	}
	
	public void removePassenger (Compartment compartment, Passenger passenger){
			compartment.getCarriables().remove(passenger);
	}
	public int getProfit(){
		return profit;
	}
}
