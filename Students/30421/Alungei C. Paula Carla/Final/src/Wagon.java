import java.util.ArrayList;

public class Wagon {
	private static final int priceTicket = 100;
	int nrOfPassengers;
	int nrOfItems;
	ArrayList<Passenger> settingPassengerWagon = new ArrayList<Passenger>();
	ArrayList<CargoItem> settingCargoWagon = new ArrayList<CargoItem>();
	void addPassenger() {
		Passenger nextWagon = new Passenger("Ion");
		settingPassengerWagon.add(nextWagon);
		nrOfPassengers++;
	}

	void addItems() {
		CargoItem nextWagon = new CargoItem("",2 );
		settingCargoWagon.add(nextWagon);
		nrOfPassengers++;
	}
	
	public int calculateProfitForPassengerWagon(){
		return nrOfPassengers * priceTicket;
	}
	
	public int calculateProfitForCargoWagon(){
	
	}
}
