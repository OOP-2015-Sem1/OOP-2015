import java.util.ArrayList;
import java.util.Collection;

public class Plane {
	private Collection<Compartment> compartamente = new ArrayList<Compartment>();
	private String planeName;
	private int planeProfit;
	public Plane(String planeName){
		this.planeName = planeName;
	}
	public String getPlaneName() {
		return planeName;
	}

	

	public void addCompartment(Compartment<Carriable> compartment) {
		compartamente.add(compartment);
	}

	public int getPlaneProfit() {
		return planeProfit;
	}

	public void setPlaneProfit(int planeProfit) {
		for(Compartment o: compartamente)
		this.planeProfit =  planeProfit+o.getFinalPrice();
	}
	public int getNrOfCompartments(){
		return compartamente.size();
	}
	public Collection<Compartment> getCompartments(){
		return this.compartamente;
	}
}
