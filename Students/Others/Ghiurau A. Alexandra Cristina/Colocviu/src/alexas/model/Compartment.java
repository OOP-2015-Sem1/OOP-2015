package alexas.model;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Compartment<T extends Carriable> {

	ArrayList<T> list = new ArrayList<>();
	final String name = UUID.randomUUID().toString();

	public String getName() {
		return name;
	}

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}

	public boolean add(T c) {
		return list.add(c);
	}

	public boolean remove(T c) {
		return list.remove(c);
	}
	
	protected abstract int getProfit();
}
