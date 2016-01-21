package source;

import java.util.*;

public class Train {

	List<Train> linkedWagons = new LinkedList<Train>();
	public String name = null;
	
	public Train (String name, Collection<? extends Train> wagons) {
		this.name = name;
		this.linkedWagons.addAll(wagons);
	}

	public int computeProfit(List<Wagon> wagons) {
		int sum = 0;

		for (Wagon w : wagons) {
			sum += w.overallProfit(wagons);
		}
		return sum;
	}

	public int numberOfWagons(List<Wagon> wagons) {
		return wagons.size();
	}

	public int overallProfit(List<Wagon> wagons) {
		// calculate profit for any type of wagon train not
		return ();
	}
}
