import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Airport {
	public ArrayList<Plane> planes;

	public Airport() {
		planes = new ArrayList<Plane>();
	}

	public void receivePlane(Plane plane) {
		planes.add(plane);
	}

	public void departPlane(Plane plane) {
		planes.remove(plane);
	}

	public boolean isPlaneInStation(Plane plane) {
		return planes.contains(plane);
	}

	public static void main(String[] args) {
		Airport airport = new Airport();

		for (int i = 0; i < 1 + (new Random()).nextInt(10); i++) {
			Plane plane = new Plane(UUID.randomUUID().toString());
			if (i % 2 == 0) {
				for (int j = 0; j < 1 + (new Random()).nextInt(10); j++) {
					plane.addCompartment(new PassangerCompartment());
				}
			} else {
				for (int j = 0; j < 1 + (new Random()).nextInt(10); j++) {
					CargoItem item = new CargoItem(UUID.randomUUID().toString(), 1 + (new Random()).nextInt(10));
					plane.addCompartment(new CargoCompartment(item));
				}
			}			
			
		}
		
		//airport.planes.sort();
		for (int i = 0; i < airport.planes.size(); i++) {
			
		}
	}
	
	
}
