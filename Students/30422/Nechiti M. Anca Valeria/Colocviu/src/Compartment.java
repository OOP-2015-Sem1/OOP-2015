import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public class Compartment {
	
	Passenger passenger;
	String passengerN=passenger.name;
	public String getPassenger(){
		return passengerN;
	}
	
	public void passengerType(){
		
		ArrayList<Carriable> carriable= new ArrayList<Carriable>();
		int nrPas = new Random().nextInt(500);
		final int max = 100;
		for(int i=0;i<nrPas;i++){
			while(i<max){
				Passenger passenger = new Passenger();
				carriable.add(passenger);
			}
		}
		
		final int price = 100;
		int profit = price * carriable.size();
		
		final int ticket = new Random().nextInt(500);
		
	}
	
	public void cargoType(){
		
		CargoItem item = new CargoItem();
		ArrayList<Carriable> carriable= new ArrayList<Carriable>();
		int nrItems = new Random().nextInt(500);
		
		for(int i=0;i<nrItems;i++){
			carriable.add(item);
		}
		
		int profit = carriable.size() * item.profit;
		
	}
	
	public final int price = new Random().nextInt(500);
	
	public Compartment(){

		final String ID = UUID.randomUUID().toString();
		
	}
	
	public static void main(String[] args) {
		
	Compartment compartment1 = new Compartment();
		
	}

}
