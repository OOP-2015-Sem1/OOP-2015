package colocviu;
import java.util.*;

public class Ship <T extends Compartment>{

	private LinkedHashSet<T> compartments;
	private String name;
	private int profit=0;
	
	public void computeProfit(){
		for(T compartment : compartments){
			profit+=compartment.getProfit();
		}
	}
	
	public int getProfit(){
		return profit;
	}
	public void addCompartments(T compartment){
		compartments.add(compartment);
	}
	public void removeCompartments(T compartment){
		compartments.remove(compartment);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public LinkedHashSet<T> getCompartments(){
		return compartments;
	}
	
}
