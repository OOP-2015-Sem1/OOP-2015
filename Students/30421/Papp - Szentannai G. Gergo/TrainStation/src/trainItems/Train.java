package trainItems;

import java.util.ArrayList;

public class Train {
	private String name;
	private ArrayList<Wagon> wagons;

	public Train(String nameOfTrain) {
		wagons = new ArrayList<Wagon>();
		System.out.println("Creating train...");
		setName(new String(nameOfTrain));
		for (int i = 0; i < 5; i++) {
			addWagon();
		}
	}

	public int getProfit() {
		int profit = 0;
		for (int i = 0; i < getNrOfWagons(); i++) {
			profit += wagons.get(i).getCarriables().get(0).getProfit();
		}
		return profit;
	}

	public void addWagon() {
		wagons.add(new Wagon());
	}

	public int getNrOfWagons() {
		return wagons.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
