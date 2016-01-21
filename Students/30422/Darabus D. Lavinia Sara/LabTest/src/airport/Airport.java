package airport;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class Airport {

	private LinkedHashSet<Plane> planeSet = new <Plane>LinkedHashSet<Plane>();
	private Iterator iterator = planeSet.iterator();
	
	public void receivePlane(Plane plane){
		planeSet.add(plane);
		plane.setPlaneInStation(true);
		
	}
	
	public void departPlane(Plane plane){
		planeSet.remove(plane);
		plane.setPlaneInStation(false);
	}
	
	public boolean checkIfPlaneIsInStation(Plane plane){
		return plane.isPlaneInStation();
	}
	
	public void sortPlanesByName(){
		while(this.iterator.hasNext()){
			Plane plane = new Plane();
			plane = (Plane) iterator.next();
			}
	}
	
	public void sortPlanesByProfit(){
		while(this.iterator.hasNext()){
			Plane plane = new Plane();
			plane = (Plane) iterator.next();
		}
	}


	
}
