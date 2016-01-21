package colocviuMariaDrambarean;

import java.util.LinkedList;
import java.util.UUID;

public abstract class Compartment {
	//methods for being able to add the items to a set, differance based on ID
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compartment other = (Compartment) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	private String ID = UUID.randomUUID().toString();
	private LinkedList<Carriable> objectList;
	private int profit;

	// method for adding a object
	public void add(Carriable object) {
		objectList.add(object);
	}

	// method for coputing profit
	public abstract void computeProfit();

	public LinkedList<Carriable> getObjectList() {
		return objectList;
	}

	public void setObjectList(LinkedList<Carriable> objectList) {
		this.objectList = objectList;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

}
