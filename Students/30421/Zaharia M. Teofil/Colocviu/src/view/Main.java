package view;

import java.util.Random;

import model.carriable.CargoItem;
import model.carriable.Passenger;
import model.wagon.CargoWagon;
import model.wagon.PassengerWagon;
import model.wagon.Wagon;

public class Main {
	public static void main(String[] args) {
		
	}
	
	public String createName() {
		Random r = new Random();
		return String.valueOf(r.nextInt(100));
	}
	
	public Passenger createPassenger() {
		Random r = new Random();
		
		return new Passenger(createName() );
	}
	
	public CargoItem createCargoItem() {
		Random r = new Random();
		
		int profit = r.nextInt(10);
		if (profit < 0) {
			profit *= -1;
		}
		return new CargoItem(createName(), profit );
	}
	
	public Wagon createWagon() {
		Random r = new Random();
		
		boolean isCargo = r.nextBoolean();
		
		if (isCargo) {
			return new CargoWagon(createName(), createCargoItem());
		} else {
			return new PassengerWagon(createName());
		}
	}
}
