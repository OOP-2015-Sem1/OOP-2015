import java.util.Random;
import java.util.UUID;

public class Compartment extends Plane {

	Passenger p = new Passenger();
	CargoItem c = new CargoItem();
	Random r = new Random();
	
	private int ticketPrice = 100;
	
	
	public Compartment(){
		String x = UUID.randomUUID().toString();
	}
	
	public void totalProfit(int total){
		
		total = p.getProfit();
		
	}
	
	public int passengerCompartment(){
		
		int carriables;
		carriables = p.getNumber();
		
		return carriables;
		
	}
	
	public int cargoProfit(){
		
		int profit;
		profit = c.getProfit()*c.getNumber();
		return profit;
		
	}
	
	public int passProfit(){
		
		int profit = passengerCompartment()*ticketPrice;
		return profit;
		
	}
	
}
