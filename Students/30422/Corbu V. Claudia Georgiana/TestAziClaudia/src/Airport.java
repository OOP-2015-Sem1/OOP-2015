import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Airport {
	List<Plane> planes = new ArrayList<Plane>();

	public void reveivePlane(Plane plane) {
		planes.add(plane);
	}

	public void departPlane(Plane plane) {
		planes.remove(plane);
	}

	public void checkIFPlaneInStation(Plane plane) {
		for (Plane p : planes) {
			if (p.getCompartments() == plane.getCompartments()) {
				System.out.println("Plane in airort");
			} else {
				System.out.println("Plane not in airport");
			}
		}

	}

	public List<Plane> getPlanesByName() {
		Collections.sort(planes, new PlaneNameComparator());
		return planes;
	}

	public class PlaneNameComparator implements Comparator {

		@Override
		public int compare(Object obj, Object arg1) {
			// comparam dupa nume...
			return 0;
		}
	}

	public List<Plane> getPlanesByProfit() {
		Collections.sort(planes, new PlaneProfitComparator());
		return planes;
	}

	public class PlaneProfitComparator implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			// comparam dupa profit total
			return 0;
		}

	}

	public static void main(String[] args) {
		Airport airport = new Airport();
		airport.reveivePlane(new Plane("Numeavion"));

	}
}