package labtest;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Airport {

	Plane plane;
	Set<Plane> planes = new HashSet<Plane>();
	Set<Plane> station = new HashSet<Plane>();
	String planeName = UUID.randomUUID().toString();

	public void receive(Set<Plane> s) {
		planes.add(new Plane(planeName));
		station.add(new Plane(planeName));
	}

	public void depart(Set<Plane> plane, Set<Plane> station) {
		Iterator it = plane.iterator();
		while (it.hasNext()) {
			if (check(station, plane))
				it.remove();
		}
	}

	public boolean check(Set<Plane> station, Set<Plane> plane) {
		Iterator it = plane.iterator();
		while (it.hasNext()) {
			if (station.contains(it.hasNext())) {
				return true;
			}
		}
		return false;
	}

	public void sortByName(Set<Plane> station) {

		Iterator it = station.iterator();
		

	}

	public void sortByProfit(Set<Plane> station) {

	}
}
