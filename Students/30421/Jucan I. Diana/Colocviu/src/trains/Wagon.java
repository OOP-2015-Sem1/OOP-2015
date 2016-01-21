package trains;

import java.util.Random;

import javax.swing.JOptionPane;

public class Wagon {

	public static final int PASSENGER_TICKET = 100;
	public static final int MAX_PASSENGERS = 100;

	// String[] wagonType = {"passenger","cargo"};

	public Wagon(String name, String wagonType) {

		final String ID = name;

		if(wagonType == "passenger"){
			Carriable passenger = new Passenger();
			String passenger1 = JOptionPane.showInputDialog("How many passenger?");
			int noOfPassenger = Integer.parseInt(passenger1); // number of train but we will
			if(noOfPassenger>MAX_PASSENGERS){
				System.out.println("too many passengers!");
			}
		}
		if(wagonType == "cargoitem"){
			Carriable cargoItem = new CargoItem();
		}

	}

	public int wagonPassengerProfit(int noOfPassenger) {
		int profit = PASSENGER_TICKET;
		profit *= noOfPassenger;
		return profit;
	}

}
