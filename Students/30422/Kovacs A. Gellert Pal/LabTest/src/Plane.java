import java.util.LinkedList;

public class Plane {
	LinkedList<Compartment> compartments;
	private String compartmentType;
	private String name;
	
	public Plane(String name) {
		this.setName(name);
		compartments = new LinkedList<Compartment>();
	}
	
	public void addCompartment(Compartment compartment) {
		if (compartments.isEmpty()) {
			compartments.add(compartment);
			this.compartmentType = compartment.getClass().getSimpleName().toString();
		}
		else if (this.compartmentType.equals(compartment.getClass().getSimpleName().toString())){
			compartments.add(compartment);
		}
		else {
			System.out.println("Compartment type is invalid for this plane.");
		}
	}
	
	public int calculateProfit() {
		int profit = 0;
		for(int i = 0; i < compartments.size(); i++) {
			profit += compartments.get(i).getProfit();
		}
		return profit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int compareTo(Plane otherPlane) {
		return name.compareTo(otherPlane.getName());
	}
	
	public String toString() {
		return "Name: " + name + " Nr. of compartments: " + compartments.size() + " Profit: " + calculateProfit();		
	}
}
