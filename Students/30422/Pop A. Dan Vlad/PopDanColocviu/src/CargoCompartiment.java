import java.util.ArrayList;

public class CargoCompartiment extends Compartment {
	private ArrayList<CargoItem> items;
	private int price;

	public CargoCompartiment(String ID, int profit) {
		super(ID, profit);
		items = new ArrayList<CargoItem>();
	}

	@Override
	public void computeProfit() {
		super.profit = price * items.size();

	}

	public void addCargoItem(CargoItem carg) {
		items.add(carg);
	}

}
