
public class CargoCompartment extends Compartment {
	private CargoItem item;
	
	public CargoCompartment(CargoItem item) {
		super();
		this.item = item;
	}
	
	@Override
	public int getProfit() {
		return carry.size() * item.getProfit();
	}
}
