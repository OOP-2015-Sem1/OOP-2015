import java.util.HashSet;
import java.util.Set;

public class Train <T extends Carriable> {

	static int identifier = 0;
	private String name;
	private Set<Wagon<T>> wagons;
	
	Train(){
		wagons = new HashSet<Wagon<T>>();
		name = "Train" + identifier++;
	}
	Train(String name) {
		wagons = new HashSet<Wagon<T>>();
		this.name = name;
	}
	
	public int getProfit() {
		int profit=0;
		for(Wagon<T> wagon : wagons) {
			profit += wagon.getProfit();
		}
		return profit;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<Wagon<T>> getWagon() {
		return wagons;
	}
	
	public void addWagon(Wagon<T> wagon){
		this.wagons.add(wagon);
	}
	
	public String getSummary() {
		return "\nNAME: " + name + "\n" + "NUMBER OF WAGONS: " + wagons.size() + "\n" + "PROFIT: " + getProfit();
	}
	
}
