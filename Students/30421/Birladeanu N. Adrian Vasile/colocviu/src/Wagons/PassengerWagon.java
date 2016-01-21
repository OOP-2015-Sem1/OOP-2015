package Wagons;

import java.util.ArrayList;
import java.util.Random;
import Carriables.Passenger;

public class PassengerWagon extends Wagon {

	private static final int maxPassengers=100;
	private int numberOfPassengers;
	private ArrayList<Passenger> passengers;
	private static final int ticket=100;
	
	public PassengerWagon(){
		Random random=new Random();
		this.numberOfPassengers=random.nextInt(maxPassengers)+1;
		passengers=new ArrayList<Passenger>();
		for(int i=0; i<numberOfPassengers; i++){
			Passenger passenger=new Passenger();
			passengers.add(passenger);
		}
	}
	
	@Override
	public int getTotalProfit() {
		return this.numberOfPassengers*ticket;
	}
	
}
