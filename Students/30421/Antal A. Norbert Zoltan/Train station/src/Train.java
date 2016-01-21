import java.util.*;

public class Train <T extends Wagon<?>> {
	private Set<T> wagons = new LinkedHashSet<T>();
	public String name;
	
	public Train(String name){
		this.name = name;
	}
	
	public int computeProfit(){
		int profit = 0;
		for (T wagon : wagons)
			profit = profit + wagon.calcProfit();
		return profit;
	}
	
	public void addWagon(T wagon){
		wagons.add(wagon);
	}
	
	public int getNrWag(){
		return wagons.size();
	}
}
