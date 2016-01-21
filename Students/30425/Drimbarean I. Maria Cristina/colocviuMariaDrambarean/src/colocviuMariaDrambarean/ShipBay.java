package colocviuMariaDrambarean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShipBay {
	private ArrayList<Ship> ships;

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	public void add(Ship ship) {
		ships.add(ship);
	}

	// sort by name
	public void sortByName() {
		Collections.sort(ships, new Comparator<Ship>() {

			@Override
			public int compare(Ship s1, Ship s2) {
				return s1.getName().compareTo(s2.getName());
			}

		});
		displayShips();
	}

	// sort by profit
	public void sortByProfit() {
		Collections.sort(ships, new Comparator<Ship>() {

			@Override
			public int compare(Ship s1, Ship s2) {
				Integer p1 = s1.getProfit();
				Integer p2 = s2.getProfit();
				return p1.compareTo(p2);
			}

		});
		displayShips();
	}

	// display the ships
	public void displayShips() {
		for (Ship s : ships) {
			System.out.print("The ship " + s.getName());
			System.out.print(" with " + s.getCompartments().size() + " compartments");
			System.out.println(" and profit: " + s.getProfit() + "!");
		}
	}
}
