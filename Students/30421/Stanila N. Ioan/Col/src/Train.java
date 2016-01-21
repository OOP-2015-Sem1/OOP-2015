import java.util.LinkedList;

public class Train {

	private String name;
	private int profit;
	
	private WagonType wagonType;
	
	private LinkedList<Wagon<Carriable>> wagons;
	
	public Train(String name) {
		this.name = name;
		wagons = new LinkedList<Wagon<Carriable>>();
	}
	
	public void addWagon(Wagon<Carriable> wagon) {
		if (wagons.size() == 0) {
			determineWagonType(wagon);
		}
		if (wagon.getWagonType() == wagonType) {
			wagons.add(wagon);
		}
		computeProfit(wagon);
	}
	
	private void determineWagonType(Wagon<Carriable> wagon) {
		if (wagon.getWagonType() == WagonType.PASSENGER) {
			wagonType = WagonType.PASSENGER;
		} else if (wagon.getWagonType() == WagonType.CARGO) {
			wagonType = WagonType.CARGO;
		}
	}
	
	private void computeProfit(Wagon<Carriable> wagon) {
		profit += wagon.getProfit();
	}
	
	public String getName() {
		return name.toString();
	}
	
	public int getProfit() {
		return profit;
	}
	
	public int getNoOfWagons() {
		return wagons.size();
	}
	
	public String toString() {
		return getName() + " " + getNoOfWagons() + " " + getProfit();
	}
}
