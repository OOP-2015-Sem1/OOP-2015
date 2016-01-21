
import java.util.ArrayList;
import java.util.UUID;

public class Compartment implements Carriable {
	final String ID = UUID.randomUUID().toString();
	int NR_PASSENGERS = 100;
	int TICKET = 100;
	boolean test;
	int profit = 0;

	public Compartment() {
		ArrayList<String> cargo = new ArrayList<String>();
		ArrayList<String> passenger = new ArrayList<String>();
		if (test == true) {
			cargo.add("Cargo");
		} else
			passenger.add("Passenger");

	}
	
}
