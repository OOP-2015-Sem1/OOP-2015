import java.util.LinkedList;

public class Ship {

	private String name;
	private int numberOfCompartments;
	private int totalProfit;
	private LinkedList<Compartment> CompartmentList = new LinkedList<Compartment>();

	public Ship(String name, Compartment compartment) {

		this.name = name;
		CompartmentList.add(compartment);
		numberOfCompartments++;
		totalProfit += compartment.getProfit();

	}

	public String getName() {
		return name;
	}

	public LinkedList<Compartment> getCompartmentList() {
		return CompartmentList;
	}

	public int getTotalProfit() {
		return totalProfit;
	}

	public int getNumberOfCompartments() {
		return numberOfCompartments;
	}

}
