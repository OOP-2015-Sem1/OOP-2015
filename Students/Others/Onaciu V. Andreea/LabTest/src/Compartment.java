
import java.util.Collection;
import java.util.UUID;

public abstract class Compartment {

	public final static int passengerTicketPrice = 10;
	public final static int maxNrOfPassengers = 100;
	private final String ID;
	int profit;

	public Compartment(){
		this.ID = UUID.randomUUID().toString();
		this.profit=0;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public int calculateProfit(){
		return this.profit;
	}
}
