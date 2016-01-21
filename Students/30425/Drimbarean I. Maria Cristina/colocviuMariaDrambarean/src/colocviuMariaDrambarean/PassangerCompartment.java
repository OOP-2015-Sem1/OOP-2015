package colocviuMariaDrambarean;

import colocviuMariaDrambarean.Constants;

public class PassangerCompartment extends Compartment {
	@Override
	public void add(Carriable object) {
		if (getObjectList().size() < Constants.maxNrOfPassengers)
			getObjectList().add(object);
	}
	 public void computeProfit(){
		 setProfit(getObjectList().size()*Constants.ticketPrice);
	 }
}
