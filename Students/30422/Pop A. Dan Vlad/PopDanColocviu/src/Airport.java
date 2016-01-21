import java.util.ArrayList;
import java.util.UUID;

public class Airport {
	private ArrayList<Plane> planes;
	private Plane p;

	public Airport() {
		planes = new ArrayList<Plane>();
		String x = UUID.randomUUID().toString();
		Plane plane = new Plane(x);
		x = UUID.randomUUID().toString();
		Plane plane1 = new Plane(x);
		receive(plane1);
		receive(plane);
	}

	public void run() {

	}

	public void receive(Plane plane) {
		for (Plane p : planes) {
			if (plane.equals(p)) {
				System.out.println("Already exists");
			}
		}
		planes.add(p);
	}

	public void depart(Plane p) {
		planes.remove(p);
	}

	public void display(ArrayList<Plane> planes) {

		for (Plane p : planes) {
			System.out.println(p.name + planes.size() + p.planeProfit);
		}
	}
}
