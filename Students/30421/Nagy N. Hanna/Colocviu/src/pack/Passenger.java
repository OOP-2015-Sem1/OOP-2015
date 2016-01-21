package pack;

import java.util.ArrayList;

public class Passenger extends Wagon implements Carriable {

	private final String name;
	private static final int PRICE = 100;
	private static final int MAX_NUMBER_OF_PASSANGERS = 100;
	private static int nrOfPassangers=0;
	
	public Passenger(String name){
		this.name=name;
	}
	
	public String getMane(){
		return this.name;
	}

	public int computeTotalProfit(ArrayList<Carriable> objects, int price) {
		
		return objects.size() * price;
	}

	public void addPassenger(Passenger passanger) {
		if (nrOfPassangers <= MAX_NUMBER_OF_PASSANGERS) {
			carriableObjects.add(passanger);
		} else {
			System.out.println("wagon is full");
		}
	}

}
