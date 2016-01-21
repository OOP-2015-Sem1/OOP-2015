import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Ship extends Compartment {

	private String compartmentName;
	private String shipName;
	private int noOfComponents;
	private int profit;

	public Ship(String shipName, int noOfComponents, int profit) {
		this.shipName = shipName;
		this.noOfComponents = noOfComponents;
		this.profit = profit;
	}

	List<Compartment> compartments = new LinkedList<Compartment>();
	private int shipProfit;

	public String getCompartmentName() {
		return compartmentName;
	}

	public void setCompartmentName(String compartmentName) {
		this.compartmentName = compartmentName;
	}

	public int calculateShipProfit(int cargoProfit, int passengerProfit) {
		int shipProfit = cargoProfit + passengerProfit;
		return shipProfit;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public int getNoOfComponents() {
		return noOfComponents;
	}

	public void setNoOfComponents(int noOfComponents) {
		this.noOfComponents = noOfComponents;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

}
