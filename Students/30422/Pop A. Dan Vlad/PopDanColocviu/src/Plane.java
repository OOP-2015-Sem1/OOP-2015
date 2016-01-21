import java.util.LinkedList;
import java.util.ListIterator;

public class Plane {
	protected int planeProfit;
	protected String planeType;
	protected String name;
	protected Compartment com;
	protected LinkedList<Compartment> compartments;

	public Plane(String name) {
		this.name = name;
		compartments = new LinkedList<Compartment>();

	}

	public void addCompartment(Compartment comp) {
		for (Compartment c : compartments) {
			if (comp.equals(c)) {
				System.out.println("Already exists");
			}
		}
		compartments.add(comp);
	}

	public void computePlaneProfit(LinkedList<Compartment> compartments) {
		planeProfit = 0;
		ListIterator itr = (ListIterator) compartments.iterator();
		while (itr.hasNext()) {
			com = (Compartment) itr.next();
			planeProfit += com.profit;
		}

	}
}