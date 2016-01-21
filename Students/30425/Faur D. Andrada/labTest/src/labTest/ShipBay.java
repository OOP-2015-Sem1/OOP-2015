package labTest;

import java.util.ArrayList;

public class ShipBay {
	public static ArrayList<Ship> ships;

	public static void receiveShip(Ship ship) {
		ships.add(ship);
	}

	public static void departShip(Ship ship) {
		ships.remove(ship);
	}

	public static int checkShip(Ship ship) {
		int i = ships.indexOf(ship);
		if (i != 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public static void sort(Ship ship) {
		// sorting alg
	}

	private static void getSummary(ArrayList<Ship> ships) {
		// TODO Auto-generated method stub

	}

	private static void sortByName(ArrayList<Ship> ships) {
		// TODO Auto-generated method stub

	}

	private static void sortByProfit(ArrayList<Ship> ships) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {

		sortByName(ships);
		sortByProfit(ships);
		getSummary(ships);
	}
}
