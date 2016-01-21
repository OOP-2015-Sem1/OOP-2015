package colocviu;

import java.util.LinkedList;

public class CompartmentCargo extends Compartment{

	
	private CargoItem cargoItem;
	private int profit;
	public void computeProfit(Compartment compartment){
		profit=compartment.getCarriables().size()*cargoItem.getProfit();
	}
	public void addCarriable(Compartment compartment, Carriable carriable){
		compartment.getCarriables().add(carriable);
	}
	public void removeCarriable(Compartment compartment, Carriable carriable){
		compartment.getCarriables().remove(carriable);
	}
	public CargoItem getCargoItem() {
		return cargoItem;
	}
	public void setCargoItem(CargoItem cargoItem) {
		this.cargoItem = cargoItem;
	}
	public int getProfit(){
		return profit;
	}
}
