package alexas.model;

public class CargoItemComp extends Compartment<CargoItem> {

	int profit = 2;

	@Override
	protected int getProfit() {
		int totalProfit = 0;
		for (Carriable cg : list) {
			//totalProfit = cg.getProfit() * list.size(); <- in cazul asta as fi declarat getProfit in interfata Carriable
			totalProfit = profit * list.size();
		}
		return totalProfit;
	}

}
