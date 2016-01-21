package model.wagon;

import java.util.LinkedList;
import java.util.UUID;

import model.cargo.Carriable;

public abstract class Wagon {
	private final UUID ID = UUID.randomUUID();
	private LinkedList<Carriable> carriables;

	public Wagon() {
		this.carriables = new LinkedList<>();
	}

	public abstract int getProfit();

	protected LinkedList<Carriable> getCarriables() {
		return carriables;
	}

	protected boolean canAdd() {
		return true;
	}

	public boolean addCarriable(Carriable carriable) {
		boolean canAdd = canAdd();
		if (canAdd) {
			return carriables.add(carriable);
		}
		return canAdd;
	}

	public boolean removeCarriable(Carriable carriable) {
		return carriables.remove(carriable);
	}
}
