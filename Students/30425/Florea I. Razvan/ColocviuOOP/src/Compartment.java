import java.util.ArrayList;
import java.util.UUID;

public class Compartment {

	// I know i should have extended this class and create two classes: PassengerCompartment and CargoCompartment
	// but i ran out of time and i already started it this way.
	public static final int PASSENGER_PRICE = 100;
	public static final int MAX_NR_PASSENGERS = 100;
	private int profit;
	private final String ID;
	private Boolean isPassenger;
	private Boolean isCargoItem;
	private ArrayList<Passenger> passengerList = new ArrayList<Passenger>();
	private ArrayList<CargoItem> cargoList = new ArrayList<CargoItem>();

	public Compartment(String type, Object obj) {
		ID = UUID.randomUUID().toString();
		if (type == "passengers") {
			isPassenger = true;
		} else if (type == "cargoItems")
			isCargoItem = true;
		
		if(isPassenger && passengerList.size() < MAX_NR_PASSENGERS){
			passengerList.add((Passenger) obj);
			profit = passengerList.size() * PASSENGER_PRICE;
		}else if(isCargoItem){
			cargoList.add((CargoItem) obj);
		}
		
	}

	public ArrayList<Passenger> getPassengerList() {
		return passengerList;
	}

	public ArrayList<CargoItem> getCargoList() {
		return cargoList;
	}

	public String getID() {
		return ID;
	}

	public Boolean getIsPassenger() {
		return isPassenger;
	}

	public Boolean getIsCargoItem() {
		return isCargoItem;
	}

	public int getProfit() {
		return profit;
	}

}
