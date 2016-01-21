package alexas.model;

import java.util.ArrayList;

public class AirPlane<T extends Compartment<?>> {

	ArrayList<T> listCollection = new ArrayList<>(); // this can hold both
														// passenger and cargo
														// compartments
	
	public int getTotalProfit() {
		int totalProfit = 0;
		for (Compartment<?> comp : listCollection) {
			totalProfit += comp.getProfit();
		}
		return totalProfit;
	}

	public boolean add(T c) {
		return listCollection.add(c);
	}

	public boolean remove(T c) {
		return listCollection.remove(c);
	}
	
	public String dispayText(){
		return "The total profit is: " + getTotalProfit() + " and the number of compartiments is: " + listCollection.size();
	}
	
}
