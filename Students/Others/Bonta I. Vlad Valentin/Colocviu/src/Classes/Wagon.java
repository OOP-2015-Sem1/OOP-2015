package Classes;

import java.util.ArrayList;
import java.util.UUID;

public class Wagon <T extends Carriable> {
	private static final int PASSENGER_PROFIT = 100;
	private static final int MAX_NR_OF_PASSENGERS = 100;

	final UUID ID = UUID.randomUUID();
	private ArrayList<?> wagonItems;
	private String type;
	public Wagon(ArrayList<?> wagonItems, String type){
		if (this.validateWagon(wagonItems, type)){
			this.wagonItems = wagonItems;
			this.type = type;
		} else {
			System.out.println("Wagon has different types of carriables");
		}
	}
	
	public Boolean validateWagon(ArrayList<?> wagonItems, String type){
		for(int i = 0; i< wagonItems.size(); i++){
			if (type.equalsIgnoreCase("Passenger") && !(wagonItems.get(i).getClass() == Passenger.class)){
				return false;
			}
			if (!type.equalsIgnoreCase("Passenger") && (wagonItems.get(i).getClass() == Passenger.class)){
				return false;
			}
		}
		return true;
	}
	
	public Wagon(String type){
		this.type = type;
	}
	
	public int computeProfit(){
		int profit = 0;
		if (this.type.endsWith("Passenger")){
			profit = PASSENGER_PROFIT * wagonItems.size();
		} else {
			for (int i = 0;i < wagonItems.size(); i++){
				CargoItem cargoItem = (CargoItem) wagonItems.get(i);
				profit += cargoItem.getProfit();
			}
		}
		return profit;
	}
	
}
