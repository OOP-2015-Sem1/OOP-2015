import java.util.ArrayList;

public class Wagon<T extends Carriable> {
	
	public static final int TICKET_PRICE = 100;
	public static final int MAX_NO_OF_PASSENGERS = 100;
	
	public final String UUID;
	
	private WagonType wagonType;
	private ArrayList<T> carriables;
	private int profit;
	
	public Wagon(String uuid) {
		UUID = uuid;
		carriables = new ArrayList<T>();
		profit = 0;
	}
	
	public void add(T carriable) {
		if (carriables.size() == 0) {
			determineWagonType(carriable);
		}
		if (carriable instanceof Passenger && wagonType == WagonType.PASSENGER) {
			addPassenger(carriable);
		} else if (carriable instanceof CargoItem && wagonType == WagonType.CARGO) {
			addCargoItem(carriable);
		} else {
			System.out.println("Wrong argument!");
		}
	}
	
	private void determineWagonType(T carriable) {
		if (carriable instanceof Passenger) {
			addPassenger(carriable);
			wagonType = WagonType.PASSENGER;
		} else if (carriable instanceof CargoItem) {
			addCargoItem(carriable);
			wagonType = WagonType.CARGO;
		}
	}
	
	private void addPassenger(T carriable) {
		if (carriables.size() < MAX_NO_OF_PASSENGERS) {
			carriables.add(carriable);
			profit += TICKET_PRICE;
		} else {
			System.out.println("Maximum number of passengers reached!");
		}
	}
	
	private void addCargoItem(T carriable) {
		carriables.add(carriable);
		profit += ((CargoItem) carriable).getProfit();
	}
	
	public int getProfit() {
		return profit;
	}
	
	public WagonType getWagonType() {
		return wagonType;
	}

}
