import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Compartment {

	private final String ID = UUID.randomUUID().toString();
	private boolean type;
	static ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	CargoItem cargo;
	private int carriables = 25;

	public Compartment(boolean type) {
		this.type = type;
		setCargo();
	}

	private void setCargo() {
		if (type) {
			cargo = new CargoItem();
			carriables = getRandom();
		} else {
			int i, nr = getRandom();
			for (i = 1; i <= nr; i++) 
				addPassenger();
		}
	}

	public void addPassenger() {
		if (passengers.size() < 100) {
			Passenger passenger = new Passenger();
			passengers.add(passenger);
		}
	}

	public int getProfit() {
		if (type) {
			return cargo.getProfit() * carriables;
		} else {
			return passengers.size() * 100;
		}

	}

	private static int getRandom() {
		Random rdm = new Random();
		int aux;
		aux = rdm.nextInt(100) + 1;
		return aux;
	}

	public String getID() {
		return ID;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

}
