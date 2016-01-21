package objects;

import java.util.UUID;

import colocviu.Constants;
import interfaces.Wagon;

public class PassengerWagon implements Wagon{

	private int nrOfPassenger;
	private final String ID = UUID.randomUUID().toString();
	private int profit;
	
	public void addPassenger(){
		if ( nrOfPassenger < Constants.MAX_NR_PASSENGERS) {
			nrOfPassenger ++ ;
		} else {
			System.out.println("ERROR - too many people !!! - cannot add ");
		}
	}

	@Override
	public int getWagonProfit() {
		// TODO Auto-generated method stub
		return profit;
	}

	@Override
	public int getNrOfCariable() {
		// TODO Auto-generated method stub
		return nrOfPassenger;
	}

	@Override
	public void setNrOfCariable(int numberOfPassengers) {
		if (numberOfPassengers <= Constants.MAX_NR_PASSENGERS) {
			nrOfPassenger = numberOfPassengers;
		} else {
			System.out.println("ERROR - too many people !!! - value set by default to 0");
			nrOfPassenger = 0;
		}
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setWagonProfit() {
		profit = getNrOfCariable() * Constants.MAX_NR_PASSENGERS; 
	}
}
