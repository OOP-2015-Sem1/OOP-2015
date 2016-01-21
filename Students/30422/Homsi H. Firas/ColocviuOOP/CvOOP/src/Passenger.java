import java.util.*;

public class Passenger {
	
	Set<String> passName = new LinkedHashSet<>();

	public boolean isCarriable = true;
	public final String NAME;
	public int profit;
	
	public Passenger(String name, int profit, boolean isCarriable){
		
		this.NAME = name;
		this.profit = profit;
		this.isCarriable = isCarriable;
	}
	
	public String getPassengerName(){
		return this.NAME;
	}
	
	public int getProfit(){
		return profit+100;
	}
	
	public boolean isCarriable(boolean isCarriable){
		return this.isCarriable;
	}
}
