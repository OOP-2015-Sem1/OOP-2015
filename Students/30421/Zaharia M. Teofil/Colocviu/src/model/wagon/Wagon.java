package model.wagon;

import java.util.HashSet;

import model.carriable.Carriable;

public abstract class Wagon {
	protected final String id;
	protected final HashSet<Carriable> carry;
	
	
	public Wagon(String id) {
		this.id = id;
		this.carry = new HashSet<Carriable>();
	}

	public abstract int getProfit();
	
	protected int getCarrySize() {
		return carry.size();
	}
	
	public String getId() {
		return id;
	}
	
	public boolean equals(Object o) {
		Wagon w = (Wagon) o;
		
		return w.getId().equals(w.getId());
	}
	
}
