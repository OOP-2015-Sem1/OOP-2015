import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Compartment {

	private final int TICKET_PRICE = 100;
	private final int MAX_NR_PASSENGER = 100;

	private final String ID = UUID.randomUUID().toString();

	private int profit;

	private ArrayList<Passenger> PassComp = new ArrayList<Passenger>();
	private ArrayList<CargoItem> CargoComp = new ArrayList<CargoItem>();

	private enum CompartmentType {
		PassengerType(), CargoType();
	}
	CompartmentType type;


	public Compartment() {
		int r = new Random().nextInt(1);
		if (r==0){
			this.type = CompartmentType.PassengerType;
			generatePassComp();
		}
		else
			this.type = CompartmentType.CargoType;
			generateCargoComp();
	}

	private void generateCargoComp() {
		// TODO Auto-generated method stub
		int r = new Random().nextInt(MAX_NR_PASSENGER);
		for(int i=0;i<=r;i++){
			CargoItem cargoItem = new CargoItem();
			CargoComp.add(cargoItem);
			
		}
	}

	private void generatePassComp() {
		// TODO Auto-generated method stub
		int r = new Random().nextInt(100);
		for(int i=0;i<=r;i++){
			Passenger passenger = new Passenger();
			PassComp.add(passenger);
			
		}
	}

	public void calculateCargoProfit() {
		for (int i = 0; i <= CargoComp.size(); i++) {
			CargoItem tempItem = CargoComp.get(i);
			profit += tempItem.getProfit();
		}
	}

	public void calculatePassengerProfit() {
		profit = PassComp.size()*TICKET_PRICE;
	}
	
	public int getProfit(){
		return profit;
	}
}
