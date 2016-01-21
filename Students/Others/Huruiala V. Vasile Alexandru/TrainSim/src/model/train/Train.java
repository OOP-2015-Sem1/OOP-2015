package model.train;

import java.util.LinkedList;

import model.wagon.Wagon;

public class Train <T extends Wagon>{
	private LinkedList<T> wagons;
	private String name;
	
	public Train(String name) {
		this.wagons = new LinkedList<>();
		this.name = name;
	}
	
	public boolean addWagon(T wagon) {
		return wagons.add(wagon);
	}
	
	public boolean removeWagon(T wagon) {
		return wagons.remove(wagon);
	}
	
	public int getProfit() {
		int profit = 0;
		for (Wagon w : wagons) {
			profit += w.getProfit();
		}
		return profit;
	}
	
	public String getSummary() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Name: ");
		sb.append(name);
		sb.append(", No. of wagons: ");
		sb.append(wagons.size());
		sb.append(", Profit: ");
		sb.append(getProfit());
		sb.append(".");
		
		return sb.toString();
	}

	public String getName() {
		return name;
	}
}
