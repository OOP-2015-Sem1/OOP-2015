package airport;

public class CargoCompartment extends Compartment {

	@Override
	public void computeProfit() {
		// TODO Auto-generated method stub
		CargoItem cargoItem = new CargoItem();
		cargoItem = (CargoItem) this.compartmentList.get(0);
		this.setProfit(this.compartmentList.size() * cargoItem.profit);
	}

}
