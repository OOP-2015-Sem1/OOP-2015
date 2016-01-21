package model.train;

import java.util.Iterator;
import java.util.LinkedHashSet;

import model.wagon.Wagon;

public class Train implements Comparable<Train> {
	private final String name;
	private final LinkedHashSet<Wagon> wagons;
	
	public Train(String name) {
		this.name = name;
		this.wagons = new LinkedHashSet<Wagon>();
	}
	
	public void addWagon(Wagon newWagon) {
		if(wagons.contains(newWagon)) {
			System.out.println("Already has that wagon");
		} else {
			wagons.add(newWagon);
		}
	}
	
	public int getProfit() {
		int totalProfit = 0;
		
		Wagon wagon;
		Iterator<Wagon> wagonIt = wagons.iterator();
		while (wagonIt.hasNext()) {
			wagon = wagonIt.next();
			totalProfit += wagon.getProfit();
		}
		
		return totalProfit;
	}
	
	public int getWagonNo() {
		return wagons.size();
	}

	public String getName() {
		return name;
	}
	
	public String getSummary() {
		return getName() + getWagonNo() + getProfit();
	}

	@Override
	public int compareTo(Train other) {
		return this.getProfit() - other.getProfit();
	}
	
	public boolean equals(Object o) {
		Train t  = (Train) o;
		return t.getName().equals(this.getName());
	}
	
	
}
