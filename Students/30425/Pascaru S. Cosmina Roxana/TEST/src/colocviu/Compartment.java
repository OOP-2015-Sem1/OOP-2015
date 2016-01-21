package colocviu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Compartment<T> {

	private final String ID = UUID.randomUUID().toString();

	private ArrayList<Carriable> objects = new ArrayList<Carriable>();

	private int profit;

	private static final int PASSANGER_PRICE = 100;

	static final int TOTAL_NR_OF_PASSANGERS = 100;

	private CargoItem cargoItem = new CargoItem();

	public <ArrayList> void addToCompartment(ArrayList list) {
		objects.addAll((Collection<? extends Carriable>) list);
	}

	public <Passanger> void passangerProfit() {
		profit = PASSANGER_PRICE * objects.size();
		System.out.println(profit);
	}

	public <CargoItem> void cargoProfit() {
		profit = objects.size() * cargoItem.getProfit();
		System.out.println(profit);
	}

}
