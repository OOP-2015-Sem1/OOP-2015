package airport;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class Plane {
	
	private LinkedHashSet<Compartment> planeCompartmentSet = new <Compartment> LinkedHashSet();
	private String name;
	private int planeProfit;
	private boolean planeInStation;
	Iterator iterator = planeCompartmentSet.iterator();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlaneProfit() {
		return planeProfit;
	}
	public void setPlaneProfit(int planeProfit) {
		this.planeProfit = planeProfit;
	}
	
	public void computePlaneProfit(){
		while(this.iterator.hasNext()){
			Compartment compartment = (Compartment) this.iterator.next();
			this.setPlaneProfit(this.getPlaneProfit() + compartment.getProfit());
		}
	}
	
	public boolean isPlaneInStation() {
		return planeInStation;
	}

	public void setPlaneInStation(boolean planeInStation) {
		this.planeInStation = planeInStation;
	}

}
