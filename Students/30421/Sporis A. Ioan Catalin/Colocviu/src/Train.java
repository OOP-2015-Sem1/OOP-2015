import java.util.LinkedHashSet;
import java.util.Set;

public class Train {
	private String name;
	public Set<Wagon> wagons = new LinkedHashSet<Wagon>();
	private int profit = 0;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void computeProfit() {
		for(Wagon wagon: this.wagons) {
			
		}
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}
}
