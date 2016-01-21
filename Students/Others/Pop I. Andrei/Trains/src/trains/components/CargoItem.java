package trains.components;

import java.util.ArrayList;
import java.util.UUID;

public class CargoItem implements Carriable{
	
	private final String name;
	private final int profit;
	private final int nrOfItems;
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public CargoItem() {
		name = UUID.randomUUID().toString();
		profit = (int) (Math.random() * 10);
		nrOfItems = (int) (Math.random() * 10);
		addTheItems();
	}
	
	
	private void addTheItems() {
		for(int i = 0; i < nrOfItems; i++) {
			Item item = new Item();
			item.setItemProfit(profit);
			items.add(item);
		}
	}
	
	public int getProfit() {
		return profit;
	}
	
	public int getNrOfItems() {
		return nrOfItems;
	}
}
