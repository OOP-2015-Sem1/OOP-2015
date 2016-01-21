import java.util.ArrayList;

public class ShipBay {
	static ArrayList<Ship> dockedShips = new ArrayList<Ship>();
	static ArrayList<Ship> departedShips = new ArrayList<Ship>();
	
	public static void addShip(String name, boolean docked, boolean type, int compartments) {
		Ship s = new Ship(name, docked, type, compartments);
		if (docked) {
			dockedShips.add(s);
		} else {
			departedShips.add(s);
		}
	}

	public static void DisplayShips() {
		for(Ship s:dockedShips){
			System.out.println(s.getName() + " " + s.getComp() + " " + s.totalProfit());
		}
	}

	public static void main(String args[]) {
		addShip("ship1", true, true, 6);
		addShip("ship2", false, false, 3);
		addShip("ship3", true, false, 3);
		addShip("ship4", true, true, 1);
		addShip("ship5", true, false, 3);
		
		DisplayShips();
				
		
	}
}
