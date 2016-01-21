package colocviu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Ship {

	LinkedHashSet<Compartment> shipCompartments = new LinkedHashSet<Compartment>();
	ArrayList<Compartment> shipCompList = new ArrayList<Compartment>();

	private int profit;

	public <Compartment> void loadCompartments(Compartment comp) {

		shipCompartments.add((colocviu.Compartment) comp);
		

	}

	public abstract void computeProfit();

	public abstract void showName();

	public abstract void showNumberOfCompartments();

}
